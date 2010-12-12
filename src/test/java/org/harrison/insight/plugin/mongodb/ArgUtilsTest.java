package org.harrison.insight.plugin.mongodb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

public class ArgUtilsTest {
    @Test
    public void testNullToString() {
	Assert.assertEquals("null", ArgUtils.toString((Object) null));
    }

    @Test
    public void testStringValue() {
	Assert.assertEquals("Slartibartfast",
		ArgUtils.toString("Slartibartfast"));
    }

    @Test
    public void testBooleanValue() {
	Assert.assertEquals("true", ArgUtils.toString(Boolean.TRUE));
    }

    @Test
    public void testByteValue() {
	Assert.assertEquals("42", ArgUtils.toString((byte) 42));
    }

    @Test
    public void testCharacterValue() {
	Assert.assertEquals("A", ArgUtils.toString('A'));
    }

    @Test
    public void testShortValue() {
	Assert.assertEquals("42", ArgUtils.toString((short) 42));
    }

    @Test
    public void testIntegerValue() {
	Assert.assertEquals("42", ArgUtils.toString(42));
    }

    @Test
    public void testLongValue() {
	Assert.assertEquals("42", ArgUtils.toString((long) 42));
    }

    @Test
    public void testFloatValue() {
	Assert.assertEquals("42.0", ArgUtils.toString((float) 42.0));
    }

    @Test
    public void testDoubleValue() {
	Assert.assertEquals("42.0", ArgUtils.toString(42.0));
    }

    @Test
    public void testBigIntegerValue() {
	Assert.assertEquals("42424242",
		ArgUtils.toString(new BigInteger("42424242")));
    }

    @Test
    public void testBigDecimalValue() {
	Assert.assertEquals("42.424242",
		ArgUtils.toString(new BigDecimal("42.424242")));
    }

    @Test
    public void testObjectIdValue() {
	Assert.assertEquals("0123456789abcd0123456789",
		ArgUtils.toString(new ObjectId("0123456789abcd0123456789")));
    }

    @Test
    public void testUnknownClass() {
	Assert.assertEquals("Random", ArgUtils.toString(new Random()));
    }
}
