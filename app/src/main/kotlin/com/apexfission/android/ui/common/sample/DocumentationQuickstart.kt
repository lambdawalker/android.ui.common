package com.apexfission.android.ui.common.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.apexfission.android.ui.common.CodeVerificationCard

/** Compiled documentation example; the host owns verification. */
@Composable
internal fun DocumentationQuickstart() {
    var code by rememberSaveable { mutableStateOf("") }
    CodeVerificationCard(
        value = code,
        onValueChange = { code = it },
        codeLabel = stringResource(R.string.code_label),
        codeContentDescription = stringResource(R.string.code_description),
        pasteLabel = stringResource(R.string.paste),
    )
}
