package hfcmon.modem;

import java.util.List;

import hfcmon.modem.model.EventLog;
import hfcmon.modem.model.ModemState;
import hfcmon.modem.utils.HtmlReader;

public final class ParseEventLog {

    protected static void clear(ModemState state) {
        state.eventLogs.clear();
        // Note: leave "state.latestEventLog" set.
    }

    /**
     * @return Returns first event log (provided as stopIfFound parameter on next call).
     */
    protected static void parse(StringBuilder input, ModemState state, long timeSeen) {
        HtmlReader reader = new HtmlReader(input);
        List<EventLog> eventLogs = state.eventLogs;

        // We retain the last two events so we know when we've reached an event we've previously encountered.
        // We have to keep the last two events, because the cable modem sometimes modifies the date of the latest event but nothing else. In this scenario, we treat this like 2 events.
        EventLog latestEventLog1 = state.latestEventLog1;
        EventLog latestEventLog2 = state.latestEventLog2;

        // Clear event logs
        eventLogs.clear();

        // Read downstream info
        reader.startSection("<table class='simpleTable'>", "</tr>", "</table>");
        int number = 0;
        for (;;) {
            String date = reader.readBetweenOrNull("<td>", "</td>");
            if (date == null) {
                break;
            }
            number++;
            long eventId = reader.readLongBetween("<td align='right'>", "</td>");
            long eventLevel = reader.readLongBetween("<td align='center'>", "</td>");
            String description = reader.readBetween("<td>", "</td>");

            // Check doesn't match log already outputted
            if (latestEventLog1 != null && isEqual(latestEventLog1, date, eventId, eventLevel, description)) {
                setEventLogToState(state, latestEventLog1, number);
                return;
            } else if (latestEventLog2 != null && isEqual(latestEventLog2, date, eventId, eventLevel, description)) {
                setEventLogToState(state, latestEventLog2, number);
                return;
            }

            // Create new event log
            EventLog eventLog = new EventLog(timeSeen, date, eventId, eventLevel, description);
            setEventLogToState(state, eventLog, number);
            eventLogs.add(eventLog);
        }
    }

    private static void setEventLogToState(ModemState state, EventLog eventLog, int number) {
        assert number >= 1;
        if (number == 1) {
            state.latestEventLog1 = eventLog;
        } else if (number == 2) {
            state.latestEventLog2 = eventLog;
        }
    }

    private static boolean isEqual(EventLog eventLog, String date, long eventId, long eventLevel, String description) {
        assert eventLog != null;
        return date.equals(eventLog.date)
                && eventId == eventLog.eventId
                && eventLevel == eventLog.eventLevel
                && description.equals(eventLog.description);
    }

}
