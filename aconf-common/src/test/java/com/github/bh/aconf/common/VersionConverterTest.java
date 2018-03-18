package com.github.bh.aconf.common;

import com.github.bh.aconf.common.utils.VersionConverter;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaobenhai
 */
public class VersionConverterTest {

    @Test
    public void convert() throws Exception {
        Assert.assertEquals(518912, VersionConverter.convert("7.235"));
        assertEquals(518912, VersionConverter.convert("7.235.0"));
        assertEquals(518912, VersionConverter.convert("7.235.256"));

        assertEquals(458752, VersionConverter.convert("7"));
        assertEquals(458752, VersionConverter.convert("7.0"));
        assertEquals(458752, VersionConverter.convert("7.0.0"));
        assertEquals(458752, VersionConverter.convert("7.256")); // 7.0.0

        assertEquals(458752, VersionConverter.convert("7.w"));
    }

    @Test
    public void parse() throws Exception {
        assertEquals("7.235.0", VersionConverter.parse(518912));

        assertEquals("7.0.0", VersionConverter.parse(458752));

        assertEquals("", VersionConverter.parse(953248925));
    }

}