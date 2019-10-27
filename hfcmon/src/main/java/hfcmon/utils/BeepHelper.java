package hfcmon.utils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public final class BeepHelper {

    private final Logger logger;

    public BeepHelper(LoggerFactory factory) {
        this.logger = factory.getLogger(this);
    }

    public void beep() {
        beep(7000, 80);
        beep(7200, 80);
    }

    public void beepReverse() {
        beep(7200, 80);
        beep(7000, 80);
    }

    private void beep(double frequency, double milliseconds) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioFormat af = clip.getFormat();

            // Generate data for audio requested
            byte[] data = generate(af, frequency, milliseconds);

            // Open and play clip
            clip.open(af, data, 0, data.length);
            play(clip);
        } catch (Exception e) {
            throw new UnexpectedException("Failed to beep", e);
        }
    }

    private void play(Clip clip) throws InterruptedException {
        final Object mutex = new Object();
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    try {
                        synchronized (mutex) {
                            mutex.notify();
                        }
                    } catch (Throwable e) {
                        logger.error("Failed to notify thread to stop waiting", e);
                    }
                    try {
                        clip.close();
                    } catch (Throwable e) {
                        logger.error("Failed to close clip", e);
                    }
                }
            }
        });
        clip.start();
        synchronized (mutex) {
            mutex.wait();
        }
    }

    private static byte[] generate(AudioFormat af, double frequency, double milliseconds) {
        if (af.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            throw new UnsupportedOperationException("Expected PCM encoding");
        } else if (af.getSampleSizeInBits() != 16) {
            throw new UnsupportedOperationException("Expected 16 bit sample size");
        } else if (af.getChannels() != 2) {
            throw new UnsupportedOperationException("Expected 2 channels");
        } else if (af.getFrameSize() != 4) {
            throw new UnsupportedOperationException("Expected 4 bytes per frame");
        }

        double framesPerSecond = af.getFrameRate();
        int frameCount = (int) (framesPerSecond * (milliseconds / 1000.0));

        // TODO 4 bytes per frame - and we're outputting a sample size of 2 bytes per sample (i.e. 16 bits).
        // Because of this have to output twice as much data... think this may be because there's 2 channels, but not sure.
        int index = 0;
        byte[] data = new byte[frameCount * 4];
        double frequencyFactor = (Math.PI / 2) * frequency / framesPerSecond;
        double volume = Short.MAX_VALUE; // Note: adjusting this value doesn't actually adjust the volume
        for (int frame = 0; frame < frameCount * 2; frame++) {
            short sample = (short) (volume * Math.sin(frame * frequencyFactor));
            data[index++] = (byte) (sample >> 8);
            data[index++] = (byte) sample;
        }
        assert index == data.length : "index=" + index + ", data.length=" + data.length;
        return data;
    }

}
