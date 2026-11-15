package com.apexfission.android.ui.common

/** Numeric filtering shared by typing and explicit paste; matches Auth's digit policy. */
internal fun normalizeOneTimeCode(value: String, codeLength: Int): String {
    require(codeLength > 0) { "codeLength must be positive" }
    return value.filter(Char::isDigit).take(codeLength)
}
