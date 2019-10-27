package hfcmon.utils;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public final class UtilsTest {

    @Test
    public void trimToNull() {
        Assert.assertNull(Utils.trimToNull(null));
        Assert.assertNull(Utils.trimToNull(""));
        Assert.assertNull(Utils.trimToNull(" "));
        Assert.assertNull(Utils.trimToNull("\n"));
        Assert.assertNull(Utils.trimToNull("\n\t"));
        Assert.assertEquals("Abc", Utils.trimToNull(" Abc"));
        Assert.assertEquals("abC", Utils.trimToNull("abC "));
        Assert.assertEquals("aBc", Utils.trimToNull(" aBc "));
        Assert.assertEquals("aBc", Utils.trimToNull("\taBc "));
        Assert.assertEquals("aBc", Utils.trimToNull(" aBc\n"));
    }

    @Test
    public void asd() {
        Assert.assertEquals("[]", Arrays.toString(Utils.parseCommaSeperatedString("")));
        Assert.assertEquals("[]", Arrays.toString(Utils.parseCommaSeperatedString("   ")));
        Assert.assertEquals("[]", Arrays.toString(Utils.parseCommaSeperatedString(" \r\n  ")));
        Assert.assertEquals("[]", Arrays.toString(Utils.parseCommaSeperatedString(" ,  \r,\n ,   ")));
        Assert.assertEquals("[a, b, c]", Arrays.toString(Utils.parseCommaSeperatedString("a,b,c")));
        Assert.assertEquals("[a, b, c]", Arrays.toString(Utils.parseCommaSeperatedString("a, b, c")));
        Assert.assertEquals("[a, b, c]", Arrays.toString(Utils.parseCommaSeperatedString(" a,  \r,\n b, ,c  ")));
        Assert.assertEquals("[a, b, c]", Arrays.toString(Utils.parseCommaSeperatedString(" ,,a,  \r,\n b, ,c  ")));
    }

}
