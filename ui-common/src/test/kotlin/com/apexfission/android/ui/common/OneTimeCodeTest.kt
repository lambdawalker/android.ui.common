package com.apexfission.android.ui.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class OneTimeCodeTest {
    @Test fun formattedPasteKeepsOnlyDigitsWithinLength() {
        assertEquals("123456", normalizeOneTimeCode("12 3-456 extra 7", 6))
    }
    @Test fun shorterCodesAndClearingAreSupported() {
        assertEquals("1234", normalizeOneTimeCode("123456", 4))
        assertEquals("", normalizeOneTimeCode("letters", 6))
        assertEquals("", normalizeOneTimeCode("", 6))
    }
    @Test fun retainsExistingAuthUnicodeDigitBehavior() {
        assertEquals("١٢٣", normalizeOneTimeCode("١٢٣", 6))
    }
    @Test fun nonpositiveLengthIsRejected() {
        assertThrows(IllegalArgumentException::class.java) { normalizeOneTimeCode("12", 0) }
        assertThrows(IllegalArgumentException::class.java) { normalizeOneTimeCode("12", -1) }
    }
}
