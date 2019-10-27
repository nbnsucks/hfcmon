package hfcmon.modem;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hfcmon.modem.model.InternetOutage;

public class InternetOutageTest {

    @Before
    public void init() {
        TimeZone zone = TimeZone.getTimeZone("Australia/Sydney");
        Assert.assertNotNull(zone);
        TimeZone.setDefault(zone);
    }

    @Test
    public void test() {
        InternetOutage obj = new InternetOutage(0);
        obj.endTime = 90000;

        Assert.assertEquals("1970-01-01 10:00:00,1970-01-01 10:01:30,,1.5,90,,", obj.toCsv());
        Assert.assertEquals("{\"startTime\":0,\"endTime\":90000,\"eventLogs\":[]}", obj.toJson());
    }

}
