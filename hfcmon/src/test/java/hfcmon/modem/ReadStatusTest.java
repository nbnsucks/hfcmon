package hfcmon.modem;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hfcmon.modem.model.ModemState;
import hfcmon.utils.Utils;

public class ReadStatusTest {

    @Before
    public void init() {
        TimeZone zone = TimeZone.getTimeZone("Australia/Sydney");
        Assert.assertNotNull(zone);
        TimeZone.setDefault(zone);
    }

    @Test
    public void test() {
        String str = Utils.getClasspathResourceAsString("/cmstatus.html");
        ModemState state = new ModemState();
        ParseStatusPage.parse(new StringBuilder(str), state);

        Assert.assertEquals(
                "1970-01-01 10:00:00,\"Fri Oct 25 15:34:14 2019\",,-1,-1,,false,,16,3.0,5.031249999999999,6.8,39.6,40.59375,40.9,150,225,,4,42.0,43.0,44.0,,0,,,\"[Downstream]\",1,Locked,QAM256,295000000,3.0,40.5,14,21,2,Locked,QAM256,303000000,3.3,40.6,7,14,3,Locked,QAM256,311000000,4.0,40.9,12,27,4,Locked,QAM256,319000000,3.9,40.7,7,26,5,Locked,QAM256,327000000,4.0,40.9,3,22,6,Locked,QAM256,335000000,4.3,40.9,12,25,7,Locked,QAM256,343000000,3.7,40.3,10,22,8,Locked,QAM256,351000000,3.5,39.6,6,20,9,Locked,QAM256,502000000,5.4,40.1,4,30,10,Locked,QAM256,510000000,6.1,40.6,13,1,11,Locked,QAM256,518000000,6.4,40.7,4,16,12,Locked,QAM256,526000000,6.5,40.6,10,0,13,Locked,QAM256,534000000,6.3,40.6,14,0,14,Locked,QAM256,542000000,6.7,40.8,12,1,15,Locked,QAM256,550000000,6.6,40.9,10,0,16,Locked,QAM256,558000000,6.8,40.8,12,0,,\"[Upstream]\",1,Locked,\"SC-QAM Upstream\",18600000,3200000,42.0,2,Locked,\"SC-QAM Upstream\",23400000,6400000,43.0,3,Locked,\"SC-QAM Upstream\",29800000,6400000,43.0,4,Locked,\"SC-QAM Upstream\",36200000,6400000,44.0",
                state.toCsv());

        Assert.assertEquals("{\"actualTime\":0,\"modemTime\":\"Fri Oct 25 15:34:14 2019\","
                + "\"timeTakenToGetStatusPage\":-1,\"timeTakenToGetEventLog\":-1,"
                + "\"isConnected\":false,"
                + "\"downPowerMin\":3.0,\"downPowerAvg\":5.031249999999999,\"downPowerMax\":6.8,"
                + "\"downSnrMin\":39.6,\"downSnrAvg\":40.59375,\"downSnrMax\":40.9,"
                + "\"corrected\":150,\"uncorrectables\":225,"
                + "\"upstreamPowerMin\":42.0,\"upstreamPowerAvg\":43.0,\"upstreamPowerMax\":44.0,"
                + "\"downstreams\":[{\"channelId\":1,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":295000000,\"power\":3.0,\"snr\":40.5,\"corrected\":14,\"uncorrectables\":21},{\"channelId\":2,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":303000000,\"power\":3.3,\"snr\":40.6,\"corrected\":7,\"uncorrectables\":14},{\"channelId\":3,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":311000000,\"power\":4.0,\"snr\":40.9,\"corrected\":12,\"uncorrectables\":27},{\"channelId\":4,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":319000000,\"power\":3.9,\"snr\":40.7,\"corrected\":7,\"uncorrectables\":26},{\"channelId\":5,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":327000000,\"power\":4.0,\"snr\":40.9,\"corrected\":3,\"uncorrectables\":22},{\"channelId\":6,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":335000000,\"power\":4.3,\"snr\":40.9,\"corrected\":12,\"uncorrectables\":25},{\"channelId\":7,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":343000000,\"power\":3.7,\"snr\":40.3,\"corrected\":10,\"uncorrectables\":22},{\"channelId\":8,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":351000000,\"power\":3.5,\"snr\":39.6,\"corrected\":6,\"uncorrectables\":20},{\"channelId\":9,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":502000000,\"power\":5.4,\"snr\":40.1,\"corrected\":4,\"uncorrectables\":30},{\"channelId\":10,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":510000000,\"power\":6.1,\"snr\":40.6,\"corrected\":13,\"uncorrectables\":1},{\"channelId\":11,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":518000000,\"power\":6.4,\"snr\":40.7,\"corrected\":4,\"uncorrectables\":16},{\"channelId\":12,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":526000000,\"power\":6.5,\"snr\":40.6,\"corrected\":10,\"uncorrectables\":0},{\"channelId\":13,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":534000000,\"power\":6.3,\"snr\":40.6,\"corrected\":14,\"uncorrectables\":0},{\"channelId\":14,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":542000000,\"power\":6.7,\"snr\":40.8,\"corrected\":12,\"uncorrectables\":1},{\"channelId\":15,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":550000000,\"power\":6.6,\"snr\":40.9,\"corrected\":10,\"uncorrectables\":0},{\"channelId\":16,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":558000000,\"power\":6.8,\"snr\":40.8,\"corrected\":12,\"uncorrectables\":0}],"
                + "\"upstreams\":[{\"channelId\":1,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":18600000,\"width\":3200000,\"power\":42.0},{\"channelId\":2,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":23400000,\"width\":6400000,\"power\":43.0},{\"channelId\":3,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":29800000,\"width\":6400000,\"power\":43.0},{\"channelId\":4,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":36200000,\"width\":6400000,\"power\":44.0}],"
                + "\"eventLogs\":[]}",
                state.toJson());

        state.timeTakenToGetStatusPage = 1000;
        state.timeTakenToGetEventLog = 1001;
        state.isConnected = true;
        Assert.assertEquals("{\"actualTime\":0,\"modemTime\":\"Fri Oct 25 15:34:14 2019\","
                + "\"timeTakenToGetStatusPage\":1000,\"timeTakenToGetEventLog\":1001,"
                + "\"isConnected\":true,"
                + "\"downPowerMin\":3.0,\"downPowerAvg\":5.031249999999999,\"downPowerMax\":6.8,"
                + "\"downSnrMin\":39.6,\"downSnrAvg\":40.59375,\"downSnrMax\":40.9,"
                + "\"corrected\":150,\"uncorrectables\":225,"
                + "\"upstreamPowerMin\":42.0,\"upstreamPowerAvg\":43.0,\"upstreamPowerMax\":44.0,"
                + "\"downstreams\":[{\"channelId\":1,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":295000000,\"power\":3.0,\"snr\":40.5,\"corrected\":14,\"uncorrectables\":21},{\"channelId\":2,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":303000000,\"power\":3.3,\"snr\":40.6,\"corrected\":7,\"uncorrectables\":14},{\"channelId\":3,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":311000000,\"power\":4.0,\"snr\":40.9,\"corrected\":12,\"uncorrectables\":27},{\"channelId\":4,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":319000000,\"power\":3.9,\"snr\":40.7,\"corrected\":7,\"uncorrectables\":26},{\"channelId\":5,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":327000000,\"power\":4.0,\"snr\":40.9,\"corrected\":3,\"uncorrectables\":22},{\"channelId\":6,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":335000000,\"power\":4.3,\"snr\":40.9,\"corrected\":12,\"uncorrectables\":25},{\"channelId\":7,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":343000000,\"power\":3.7,\"snr\":40.3,\"corrected\":10,\"uncorrectables\":22},{\"channelId\":8,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":351000000,\"power\":3.5,\"snr\":39.6,\"corrected\":6,\"uncorrectables\":20},{\"channelId\":9,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":502000000,\"power\":5.4,\"snr\":40.1,\"corrected\":4,\"uncorrectables\":30},{\"channelId\":10,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":510000000,\"power\":6.1,\"snr\":40.6,\"corrected\":13,\"uncorrectables\":1},{\"channelId\":11,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":518000000,\"power\":6.4,\"snr\":40.7,\"corrected\":4,\"uncorrectables\":16},{\"channelId\":12,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":526000000,\"power\":6.5,\"snr\":40.6,\"corrected\":10,\"uncorrectables\":0},{\"channelId\":13,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":534000000,\"power\":6.3,\"snr\":40.6,\"corrected\":14,\"uncorrectables\":0},{\"channelId\":14,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":542000000,\"power\":6.7,\"snr\":40.8,\"corrected\":12,\"uncorrectables\":1},{\"channelId\":15,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":550000000,\"power\":6.6,\"snr\":40.9,\"corrected\":10,\"uncorrectables\":0},{\"channelId\":16,\"lock\":\"Locked\",\"modulation\":\"QAM256\",\"frequency\":558000000,\"power\":6.8,\"snr\":40.8,\"corrected\":12,\"uncorrectables\":0}],"
                + "\"upstreams\":[{\"channelId\":1,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":18600000,\"width\":3200000,\"power\":42.0},{\"channelId\":2,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":23400000,\"width\":6400000,\"power\":43.0},{\"channelId\":3,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":29800000,\"width\":6400000,\"power\":43.0},{\"channelId\":4,\"lock\":\"Locked\",\"channelType\":\"SC-QAM Upstream\",\"frequency\":36200000,\"width\":6400000,\"power\":44.0}],"
                + "\"eventLogs\":[]}",
                state.toJson());
    }

}
