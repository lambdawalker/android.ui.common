package com.apexfission.android.ui.common

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    @Test fun replacingASelectedDigitAdvancesToTheNextDigit() {
        val previous = TextFieldValue("123456", TextRange(2, 3))
        val next = normalizeCodeEdit(previous, TextFieldValue("129456", TextRange(3)), 6)
        assertEquals("129456", next.text)
        assertEquals(TextRange(3, 4), next.selection)
        val sameDigit = normalizeCodeEdit(previous, TextFieldValue("123456", TextRange(3)), 6)
        assertEquals(TextRange(3, 4), sameDigit.selection)
    }
    @Test fun filteringAndTruncationKeepTheCursorAtTheAcceptedEnd() {
        val raw = "12 34-56 7"
        val next = normalizeCodeEdit(TextFieldValue(""), TextFieldValue(raw, TextRange(raw.length)), 6)
        assertEquals("123456", next.text)
        assertEquals(TextRange(6), next.selection)
    }
    @Test fun movingSelectionDoesNotChangeItAndDeletionKeepsACaret() {
        val previous = TextFieldValue("123456", TextRange(6))
        val selected = TextFieldValue("123456", TextRange(2, 3))
        assertEquals(selected, normalizeCodeEdit(previous, selected, 6))
        val deleted = normalizeCodeEdit(selected, TextFieldValue("12456", TextRange(2)), 6)
        assertEquals("12456", deleted.text)
        assertEquals(TextRange(2), deleted.selection)
    }

}
