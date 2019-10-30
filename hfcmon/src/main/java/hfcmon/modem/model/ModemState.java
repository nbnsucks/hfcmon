package hfcmon.modem.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hfcmon.Constants;
import hfcmon.utils.ObjectBase;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.JsonWriter;

public final class ModemState extends ObjectBase {

    public long actualTime;
    public String modemTime;

    public long timeTakenToGetStatusPage = -1; // Will be -1 if failed to request status page
    public long timeTakenToGetEventLog = -1; // Will be -1 if failed to request event log

    public Boolean isConnected; // True if connected, false if not connected - null if unknown.

    // Calculated when "calculateStats()" is called
    public double downPowerMin, downPowerAvg, downPowerMax;
    public double downSnrMin, downSnrAvg, downSnrMax;
    public long corrected, uncorrectables;
    public double upstreamPowerMin, upstreamPowerAvg, upstreamPowerMax;

    public final List<Downstream> downstreams = new ArrayList<>();
    public final List<Upstream> upstreams = new ArrayList<>();

    public final List<EventLog> eventLogs = new ArrayList<>(); // In reverse order

    public EventLog latestEventLog1; // Latest event log (1) - the first event log in the list on the "Event Log" page.
    public EventLog latestEventLog2; // Latest event log (2) - the second event log in the list on the "Event Log" page (unfortunately have to do this because sometimes the cable modem changes the date of the first event!).

    public String getEventLogsAsStringOrNull() { // Used to write CSV cell below.
        List<EventLog> eventLogs = this.eventLogs;
        int count = eventLogs.size();
        if (count <= 0) {
            return null;
        }

        // Output event logs in correct order (which means we have to go through the list from last to first).
        StringBuilder buffer = new StringBuilder();
        eventLogs.get(--count).write(buffer);
        while (count > 0) {
            buffer.append(Constants.NEW_LINE);
            eventLogs.get(--count).write(buffer);
        }
        return buffer.toString();
    }

    public static void writeHeading(CellWriter writer) throws IOException {
        writer.write("Actual Time").write("Modem Time").write();

        writer.write("Time Taken to get Status Page (ms)")
                .write("Time Taken to get Event Log (ms)").write();

        writer.write("Internet connected?").write();

        writer.write("Downstreams")
                .write("Power Min").write("Power Avg").write("Power Max")
                .write("SNR Min").write("SNR Avg").write("SNR Max")
                .write("Corrected").write("Uncorrectables").write();

        writer.write("Upstreams")
                .write("Power Min").write("Power Avg").write("Power Max").write();

        writer.write("Event Logs")
                .write("Event Info").write();

        writer.write("Detailed Stats");
    }

    @Override
    public void write(CellWriter writer) throws IOException {
        writer.writeTimestamp(actualTime).write(modemTime).write();

        writer.write(timeTakenToGetStatusPage)
                .write(timeTakenToGetEventLog).write();

        { // Write isConnected
            Boolean isConnected = this.isConnected;
            if (isConnected != null) {
                writer.write(isConnected.booleanValue() ? "Yes" : "No");
            } else {
                writer.write("Unknown");
            }
            writer.write();
        }

        writer.write(downstreams.size())
                .write(downPowerMin).write(downPowerAvg).write(downPowerMax)
                .write(downSnrMin).write(downSnrAvg).write(downSnrMax)
                .write(corrected).write(uncorrectables).write();

        writer.write(upstreams.size())
                .write(upstreamPowerMin).write(upstreamPowerAvg).write(upstreamPowerMax).write();

        writer.write(eventLogs.size());
        writer.write(getEventLogsAsStringOrNull());

        writer.write().write("[Downstream]");
        for (Downstream downstream : downstreams) {
            downstream.write(writer);
        }
        writer.write().write("[Upstream]");
        for (Upstream upstream : upstreams) {
            upstream.write(writer);
        }
    }

    @Override
    public void write(JsonWriter writer) throws IOException {
        writer.openObject();
        writer.write("actualTime", actualTime);
        writer.write("modemTime", modemTime);

        writer.write("timeTakenToGetStatusPage", timeTakenToGetStatusPage);
        writer.write("timeTakenToGetEventLog", timeTakenToGetEventLog);

        { // Write isConnected
            Boolean isConnected = this.isConnected;
            if (isConnected != null) {
                writer.write("isConnected", isConnected.booleanValue());
            } else {
                writer.write("isConnected", null);
            }
        }

        writer.write("downPowerMin", downPowerMin);
        writer.write("downPowerAvg", downPowerAvg);
        writer.write("downPowerMax", downPowerMax);

        writer.write("downSnrMin", downSnrMin);
        writer.write("downSnrAvg", downSnrAvg);
        writer.write("downSnrMax", downSnrMax);

        writer.write("corrected", corrected);
        writer.write("uncorrectables", uncorrectables);

        writer.write("upstreamPowerMin", upstreamPowerMin);
        writer.write("upstreamPowerAvg", upstreamPowerAvg);
        writer.write("upstreamPowerMax", upstreamPowerMax);

        writer.openArray("downstreams");
        for (Downstream downstream : downstreams) {
            downstream.write(writer);
        }
        writer.closeArray();

        writer.openArray("upstreams");
        for (Upstream upstream : upstreams) {
            upstream.write(writer);
        }
        writer.closeArray();

        writer.openArray("eventLogs");
        for (EventLog eventLog : eventLogs) {
            eventLog.write(writer);
        }
        writer.closeArray();

        writer.closeObject();
    }

    public void clearStats() {
        clearDownstreamStats();
        clearUpstreamStats();
    }

    public void calculateStats() {
        calculateDownstreamStats();
        calculateUpstreamStats();
    }

    private void clearDownstreamStats() {
        this.downPowerMin = -1;
        this.downPowerAvg = -1;
        this.downPowerMax = -1;

        this.downSnrMin = -1;
        this.downSnrAvg = -1;
        this.downSnrMax = -1;

        this.corrected = -1;
        this.uncorrectables = -1;
    }

    private void calculateDownstreamStats() {
        List<Downstream> downstreams = this.downstreams;
        int count = downstreams.size();
        if (count <= 0) {
            clearDownstreamStats();
            return;
        }
        double downPowerMin, downPowerAvg, downPowerMax;
        double downSnrMin, downSnrAvg, downSnrMax;
        Downstream downstream = downstreams.get(0);
        downPowerMin = downPowerAvg = downPowerMax = downstream.power;
        downSnrMin = downSnrAvg = downSnrMax = downstream.snr;
        long corrected = downstream.corrected;
        long uncorrectables = downstream.uncorrectables;
        for (int index = 1; index < count; index++) {
            downstream = downstreams.get(index);
            corrected += downstream.corrected;
            uncorrectables += downstream.uncorrectables;

            // Add power
            double value = downstream.power;
            downPowerAvg += value;
            if (value < downPowerMin) {
                downPowerMin = value;
            }
            if (value > downPowerMax) {
                downPowerMax = value;
            }

            // Add snr
            value = downstream.snr;
            downSnrAvg += value;
            if (value < downSnrMin) {
                downSnrMin = value;
            }
            if (value > downSnrMax) {
                downSnrMax = value;
            }
        }
        this.downPowerMin = downPowerMin;
        this.downPowerAvg = downPowerAvg / count;
        this.downPowerMax = downPowerMax;

        this.downSnrMin = downSnrMin;
        this.downSnrAvg = downSnrAvg / count;
        this.downSnrMax = downSnrMax;

        this.corrected = corrected;
        this.uncorrectables = uncorrectables;
    }

    private void clearUpstreamStats() {
        this.upstreamPowerMin = -1;
        this.upstreamPowerAvg = -1;
        this.upstreamPowerMax = -1;
    }

    private void calculateUpstreamStats() {
        List<Upstream> upstreams = this.upstreams;
        int count = upstreams.size();
        if (count <= 0) {
            clearUpstreamStats();
            return;
        }
        double upstreamPowerMin, upstreamPowerAvg, upstreamPowerMax;
        Upstream upstream = upstreams.get(0);
        upstreamPowerMin = upstreamPowerAvg = upstreamPowerMax = upstream.power;
        for (int index = 1; index < count; index++) {
            upstream = upstreams.get(index);

            // Add power
            double value = upstream.power;
            upstreamPowerAvg += value;
            if (value < upstreamPowerMin) {
                upstreamPowerMin = value;
            }
            if (value > upstreamPowerMax) {
                upstreamPowerMax = value;
            }
        }
        this.upstreamPowerMin = upstreamPowerMin;
        this.upstreamPowerAvg = upstreamPowerAvg / count;
        this.upstreamPowerMax = upstreamPowerMax;
    }

}