# Quickstart

Keep the value in the host. A complete code is input readiness, not proof of verification.

<!-- example: quickstart -->
```kotlin
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
```
<!-- end-example: quickstart -->

Resolve strings from the host's resources. Pass `enabled = false` while an operation owns the
input, or provide `busyMessage`. Render errors with `errorMessage` and attempts/resend help in
`supportingContent`. Supply secondary actions through `actions`; the host must disable those
callbacks when busy. Verify and resend requests, navigation and countdowns remain host concerns.

Use `OneTimeCodeField` by itself for the row of code slots and optional paste action.
Autofocus and channel-specific autofill are explicit opt-ins. Never log the code.
