package hfcmon.modem;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hfcmon.modem.model.EventLog;
import hfcmon.modem.model.ModemState;
import hfcmon.utils.ObjectBase;
import hfcmon.utils.Utils;

public class ReadEventLogTest {

    @Before
    public void init() {
        TimeZone zone = TimeZone.getTimeZone("Australia/Sydney");
        Assert.assertNotNull(zone);
        TimeZone.setDefault(zone);
    }
    
    @Test
    public void test1() {
        String str = Utils.getClasspathResourceAsString("/cmeventlog.html");
        ModemState state = new ModemState();
        ParseEventLog.parse(new StringBuilder(str), state, 0);

        Assert.assertEquals("\"SYNC Timing Synchronization failure - Failed to acquire QAM/QPSK symbol timing;;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=00:00:00:00:00:00;CM-QOS=1.1;CM-VER=3.1;\" (date=01/01/1970 00:00, eventId=84000100, eventLevel=3)\r\n" + 
                "\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=01/01/1970 00:00, eventId=82000200, eventLevel=3)\r\n" + 
                "\"Honoring MDD; IP provisioning mode = IPv6\" (date=01/01/1970 00:00, eventId=2436694066, eventLevel=6)\r\n" + 
                "\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:19, eventId=73040100, eventLevel=6)\r\n" + 
                "\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000500, eventLevel=3)\r\n" + 
                "\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000600, eventLevel=3)\r\n" + 
                "\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000800, eventLevel=3)\r\n" + 
                "\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000200, eventLevel=3)\r\n" + 
                "\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:33, eventId=82000500, eventLevel=3)",
                state.getEventLogsAsStringOrNull());

        Assert.assertEquals("1970-01-01 10:00:00,\"10/25/2019 15:33\",82000500,3,\"\"\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000200,3,\"\"\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000800,3,\"\"\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000600,3,\"\"\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000500,3,\"\"\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:19\",73040100,6,\"\"\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"01/01/1970 00:00\",2436694066,6,\"\"\"Honoring MDD; IP provisioning mode = IPv6\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"01/01/1970 00:00\",82000200,3,\"\"\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"01/01/1970 00:00\",84000100,3,\"\"\"SYNC Timing Synchronization failure - Failed to acquire QAM/QPSK symbol timing;;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=00:00:00:00:00:00;CM-QOS=1.1;CM-VER=3.1;\"\"\"",
                ObjectBase.toCsv(state.eventLogs));

        Assert.assertEquals("{\"timeSeen\":0,\"date\":\"10/25/2019 15:33\",\"eventId\":82000500,\"eventLevel\":3,\"description\":\"\\\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000200,\"eventLevel\":3,\"description\":\"\\\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000800,\"eventLevel\":3,\"description\":\"\\\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000600,\"eventLevel\":3,\"description\":\"\\\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000500,\"eventLevel\":3,\"description\":\"\\\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:19\",\"eventId\":73040100,\"eventLevel\":6,\"description\":\"\\\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"01/01/1970 00:00\",\"eventId\":2436694066,\"eventLevel\":6,\"description\":\"\\\"Honoring MDD; IP provisioning mode = IPv6\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"01/01/1970 00:00\",\"eventId\":82000200,\"eventLevel\":3,\"description\":\"\\\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"01/01/1970 00:00\",\"eventId\":84000100,\"eventLevel\":3,\"description\":\"\\\"SYNC Timing Synchronization failure - Failed to acquire QAM/QPSK symbol timing;;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=00:00:00:00:00:00;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "", ObjectBase.toJson(state.eventLogs));
    }

    @Test
    public void test2() {
        String str = Utils.getClasspathResourceAsString("/cmeventlog.html");
        ModemState state = new ModemState();
        state.latestEventLog1 = new EventLog(0,"01/01/1970 00:00", 2436694066l, 6, "\"Honoring MDD; IP provisioning mode = IPv6\"");
        ParseEventLog.parse(new StringBuilder(str), state, 0);
        test2(state);
    }
    
    @Test
    public void test2_2() {
        String str = Utils.getClasspathResourceAsString("/cmeventlog.html");
        ModemState state = new ModemState();
        state.latestEventLog2 = new EventLog(0,"01/01/1970 00:00", 2436694066l, 6, "\"Honoring MDD; IP provisioning mode = IPv6\"");
        ParseEventLog.parse(new StringBuilder(str), state, 0);
        test2(state);
    }

    private static void test2(ModemState state) {
        Assert.assertEquals("\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:19, eventId=73040100, eventLevel=6)\r\n" + 
                "\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000500, eventLevel=3)\r\n" + 
                "\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000600, eventLevel=3)\r\n" + 
                "\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000800, eventLevel=3)\r\n" + 
                "\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:25, eventId=82000200, eventLevel=3)\r\n" + 
                "\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\" (date=10/25/2019 15:33, eventId=82000500, eventLevel=3)",
                state.getEventLogsAsStringOrNull());

        Assert.assertEquals("1970-01-01 10:00:00,\"10/25/2019 15:33\",82000500,3,\"\"\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000200,3,\"\"\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000800,3,\"\"\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000600,3,\"\"\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:25\",82000500,3,\"\"\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"\r\n" + 
                "1970-01-01 10:00:00,\"10/25/2019 15:19\",73040100,6,\"\"\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\"\"\"",
                ObjectBase.toCsv(state.eventLogs));

        Assert.assertEquals("{\"timeSeen\":0,\"date\":\"10/25/2019 15:33\",\"eventId\":82000500,\"eventLevel\":3,\"description\":\"\\\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000200,\"eventLevel\":3,\"description\":\"\\\"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000800,\"eventLevel\":3,\"description\":\"\\\"16 consecutive T3 timeouts while trying to range on upstream channel 0;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000600,\"eventLevel\":3,\"description\":\"\\\"Unicast Maintenance Ranging attempted - No response - Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:25\",\"eventId\":82000500,\"eventLevel\":3,\"description\":\"\\\"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "{\"timeSeen\":0,\"date\":\"10/25/2019 15:19\",\"eventId\":73040100,\"eventLevel\":6,\"description\":\"\\\"TLV-11 - unrecognized OID;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;\\\"\"},\r\n" + 
                "", ObjectBase.toJson(state.eventLogs));
    }

}
