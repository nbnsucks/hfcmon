package hfcmon.output;

import java.io.IOException;

import hfcmon.modem.model.InternetOutage;
import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.UnexpectedException;
import hfcmon.utils.io.CellWriter;
import hfcmon.utils.io.CellWriterUtils;
import hfcmon.utils.io.JsonWriter;
import hfcmon.utils.io.JsonWriterUtils;

public final class OutputOutageImpl implements OutputOutage {

    private final Logger logger;
    private CellWriter outageCsv;
    private JsonWriter outageJson;

    public OutputOutageImpl(LoggerFactory factory, OutputConfig config) {
        logger = factory.getLogger(this);
        long now = System.currentTimeMillis();
        outageCsv = CellWriterUtils.getCellWriterOrNull(config.getOutageCsv(now), InternetOutage::writeHeading);
        outageJson = JsonWriterUtils.getJsonWriterOrNull(config.getOutageJson(now));
    }

    @Override
    public void output(InternetOutage outage) {
        {
            CellWriter outageCsv = this.outageCsv;
            if (outageCsv != null) {
                try {
                    outage.write(outageCsv);
                    outageCsv.newline();
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write csv row to \"" + outageCsv.getFilename() + "\"", e);
                }
            }
        }
        {
            JsonWriter outageJson = this.outageJson;
            if (outageJson != null) {
                try {
                    outage.write(outageJson);
                    outageJson.nextRecord();
                } catch (IOException e) {
                    throw new UnexpectedException("Failed to write json row to \"" + outageJson.getFilename() + "\"", e);
                }
            }
        }
    }

    @Override
    public void close() {
        Logger logger = this.logger;
        {
            CellWriter outageCsv = this.outageCsv;
            if (outageCsv != null) {
                this.outageCsv = null;
                outageCsv.close(logger);
            }
        }
        {
            JsonWriter outageJson = this.outageJson;
            if (outageJson != null) {
                this.outageJson = null;
                outageJson.close(logger);
            }
        }
    }

}
