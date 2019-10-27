package hfcmon.modem;

import java.util.Collections;
import java.util.List;

import hfcmon.modem.model.Downstream;
import hfcmon.modem.model.ModemState;
import hfcmon.modem.model.Upstream;
import hfcmon.modem.utils.HtmlReader;

public final class ParseStatusPage {

    protected static void clear(ModemState state) {
        state.modemTime = null;
        state.downstreams.clear();
        state.upstreams.clear();
        state.clearStats();
    }

    protected static void parse(StringBuilder input, ModemState state) {
        HtmlReader reader = new HtmlReader(input);

        // Read downstream info
        reader.startSection("<strong>Downstream Bonded Channels</strong>", "<tr align='left'>", "</table>");
        List<Downstream> downstreams = state.downstreams;
        downstreams.clear();
        for (;;) {
            String channelId = reader.readBetweenOrNull("<td>", "</td>");
            if (channelId == null) {
                reader.finishSection();
                break;
            }
            downstreams.add(new Downstream(channelId, reader));
        }
        Collections.sort(downstreams);

        // Read upstream info
        reader.startSection("<strong>Upstream Bonded Channels</strong>", "<tr align='left'>", "</table>");
        List<Upstream> upstreams = state.upstreams;
        upstreams.clear();
        for (;;) {
            String channel = reader.readBetweenOrNull("<td>", "</td>");
            if (channel == null) {
                reader.finishSection();
                break;
            }
            upstreams.add(new Upstream(reader));
        }
        Collections.sort(upstreams);

        // Read system time
        state.modemTime = reader.readBetween("<strong>Current System Time:</strong> ", "</p>");

        // Finally calculate statistics
        state.calculateStats();
    }

}
