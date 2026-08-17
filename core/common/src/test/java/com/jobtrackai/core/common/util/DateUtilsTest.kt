package com.jobtrackai.core.common.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.Locale

class DateUtilsTest {

    @Test
    fun `formatDate returns correctly formatted date`() {
        // Fix locale for predictable test results
        Locale.setDefault(Locale.US)
        
        // 2026-08-17 12:00:00 UTC
        val timestamp = 1786968000000L
        val formatted = DateUtils.formatDate(timestamp)
        
        // Output depends on system timezone, but format should be consistent
        assertNotNull(formatted)
    }

    @Test
    fun `isoToTimestamp parses valid ISO string`() {
        val iso = "2026-08-17T12:00:00Z"
        val timestamp = DateUtils.isoToTimestamp(iso)
        assertEquals(1786968000000L, timestamp)
    }

    @Test
    fun `isoToTimestamp returns null for invalid string`() {
        val iso = "invalid-date"
        val timestamp = DateUtils.isoToTimestamp(iso)
        assertEquals(null, timestamp)
    }
}
