package com.gtm;

import com.gtm.pustak.kotlin.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void getTimeWithoutSecondsTest() {
        assertEquals("17 DEC 03:53 PM", Utils.Companion.getTimeWithoutSeconds("17 DEC 03:53:12 PM"));
    }

}
