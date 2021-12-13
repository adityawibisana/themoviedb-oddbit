package com.aw.themoviedboddbit

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilsTest {
    val utils = Utils()

    @Test
    fun test_minutesToHourMinute() {
        assertEquals(utils.minutesToHourMinute(120), "2 hours")
        assertEquals(utils.minutesToHourMinute(121), "2 hours 1 minute")
        assertEquals(utils.minutesToHourMinute(122), "2 hours 2 minutes")
        assertEquals(utils.minutesToHourMinute(248), "4 hours 8 minutes")
        assertEquals(utils.minutesToHourMinute(60), "1 hour")
        assertEquals(utils.minutesToHourMinute(61), "1 hour 1 minute")
        assertEquals(utils.minutesToHourMinute(62), "1 hour 2 minutes")
        assertEquals(utils.minutesToHourMinute(72), "1 hour 12 minutes")
    }
}