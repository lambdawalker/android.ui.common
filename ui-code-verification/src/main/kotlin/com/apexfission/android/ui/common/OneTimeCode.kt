package com.apexfission.android.ui.common

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

/** Numeric filtering shared by typing and explicit paste; matches Auth's digit policy. */
internal fun normalizeOneTimeCode(value: String, codeLength: Int): String {
    require(codeLength > 0) { "codeLength must be positive" }
    return value.filter(Char::isDigit).take(codeLength)
}

/** Preserve cursor/selection offsets through digit filtering and advance replacement to the next cell. */
internal fun normalizeCodeEdit(
    previous: TextFieldValue,
    proposed: TextFieldValue,
    codeLength: Int,
): TextFieldValue {
    val text = normalizeOneTimeCode(proposed.text, codeLength)
    fun offset(index: Int) = proposed.text.take(index).count(Char::isDigit).coerceAtMost(text.length)
    var selection = TextRange(offset(proposed.selection.start), offset(proposed.selection.end))
    val completedReplacement = !previous.selection.collapsed && selection.collapsed && selection.start == previous.selection.max
    if ((text != previous.text || completedReplacement) && proposed.composition == null && proposed.text.length >= previous.text.length && selection.collapsed && selection.end < text.length) {
        selection = TextRange(selection.end, selection.end + 1)
    }
    val composition = proposed.composition?.let {
        TextRange(offset(it.start), offset(it.end)).takeUnless { range -> range.collapsed }
    }
    return TextFieldValue(text, selection, composition)
}
