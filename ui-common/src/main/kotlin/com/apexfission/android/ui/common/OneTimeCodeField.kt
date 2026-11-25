// Extracted and adapted from android.apexfission.auth; owner licensed Apache-2.0.
package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * One logical numeric field with decorative slots. The host owns [value] and validation.
 * [codeLength] must be positive. Paste is read only after tapping [pasteLabel]; null hides it.
 * Autofill is opt-in with a channel-appropriate [autofillContentType]. No code is submitted here.
 */
@Suppress("DEPRECATION")
@Composable
public fun OneTimeCodeField(
    value: String,
    onValueChange: (String) -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    codeLength: Int = 6,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    pasteLabel: String? = null,
    autofillContentType: ContentType? = null,
) {
    require(codeLength > 0) { "codeLength must be positive" }
    val focus = remember { FocusRequester() }
    var focused by remember { mutableStateOf(false) }
    val clipboard = LocalClipboardManager.current
    val autofillManager = LocalAutofillManager.current
    val update: (String) -> Unit = { raw ->
        if (enabled) onValueChange(normalizeOneTimeCode(raw, codeLength))
    }
    LaunchedEffect(autoFocus, enabled) { if (autoFocus && enabled) focus.requestFocus() }
    DisposableEffect(autofillManager, autofillContentType) {
        onDispose { if (autofillContentType != null) autofillManager?.cancel() }
    }
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        BasicTextField(
            value = value,
            onValueChange = update,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp).focusRequester(focus)
                .onFocusChanged { focused = it.isFocused }
                .then(if (autofillContentType != null) Modifier.contentType(autofillContentType) else Modifier)
                .semantics { this.contentDescription = contentDescription },
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().clearAndSetSemantics {},
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(codeLength) { index ->
                            Surface(modifier = Modifier.weight(1f).heightIn(min = 50.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                border = if (focused && enabled && index == value.length.coerceAtMost(codeLength - 1))
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                                shape = MaterialTheme.shapes.small) {
                                Box(Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                    Text(value.getOrNull(index)?.toString() ?: "",
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.titleLarge,
                                        textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                    // Keep editing, selection and accessibility; only the decorative cells paint the value.
                    Box(Modifier.matchParentSize().drawWithContent {}) { innerTextField() }
                }
            },
        )
        if (pasteLabel != null) {
            TextButton(enabled = enabled, shape = MaterialTheme.shapes.small, onClick = {
                if (enabled) {
                    clipboard.getText()?.text?.let(update)
                    focus.requestFocus()
                }
            }) { Text(pasteLabel) }
        }
    }
}
