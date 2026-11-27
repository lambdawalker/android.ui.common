// Extracted and adapted from android.apexfission.auth; owner licensed Apache-2.0.
package com.apexfission.android.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * One logical numeric field with decorative slots. The host owns [value] and validation.
 * [codeLength] must be positive. Paste is read only after tapping [pasteLabel]; null hides it.
 * Tapping a filled slot selects that digit for replacement; typing advances the focus outline.
 * Autofill is opt-in with a channel-appropriate [autofillContentType]. No code is submitted here.
 */
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
    val editor = rememberCodeEditor(value)
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CodeInput(editor, onValueChange, contentDescription, codeLength, enabled, autoFocus, autofillContentType)
        if (pasteLabel != null) CodePasteButton(editor, onValueChange, pasteLabel, codeLength, enabled)
    }
}

/** Editing selection is local; the caller's string remains authoritative. */
@Stable
internal class CodeEditor(value: String) {
    var fieldValue by mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    val focusRequester = FocusRequester()
}

@Composable
internal fun rememberCodeEditor(value: String): CodeEditor {
    val editor = remember { CodeEditor(value) }
    if (editor.fieldValue.text != value) editor.fieldValue = TextFieldValue(value, TextRange(value.length))
    return editor
}

@Composable
internal fun CodeInput(
    editor: CodeEditor,
    onValueChange: (String) -> Unit,
    contentDescription: String,
    codeLength: Int,
    enabled: Boolean,
    autoFocus: Boolean,
    autofillContentType: ContentType?,
    errorMessage: String? = null,
) {
    require(codeLength > 0) { "codeLength must be positive" }
    var focused by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current
    val direction = LocalLayoutDirection.current
    val autofillManager = LocalAutofillManager.current
    LaunchedEffect(autoFocus, enabled) { if (autoFocus && enabled) editor.focusRequester.requestFocus() }
    DisposableEffect(autofillManager, autofillContentType) {
        onDispose { if (autofillContentType != null) autofillManager?.cancel() }
    }
    BasicTextField(
        value = editor.fieldValue,
        onValueChange = { proposed ->
            if (enabled) {
                val previous = editor.fieldValue
                val next = normalizeCodeEdit(previous, proposed, codeLength)
                editor.fieldValue = next
                if (next.text != previous.text) onValueChange(next.text)
            }
        },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp).focusRequester(editor.focusRequester)
            .onFocusChanged { focused = it.isFocused }
            .then(if (autofillContentType != null) Modifier.contentType(autofillContentType) else Modifier)
            .semantics {
                this.contentDescription = contentDescription
                if (errorMessage != null) error(errorMessage)
            },
        decorationBox = { innerTextField ->
            Box(Modifier.fillMaxWidth()) {
                CodeInputCells(editor.fieldValue.text, editor.fieldValue.selection, codeLength, focused && enabled,
                    isError = errorMessage != null)
                // One real editor supplies keyboard, accessibility and autofill. Cells paint its selection.
                Box(Modifier.matchParentSize().drawWithContent {}) { innerTextField() }
                // Map touches to the cell layout, rather than the invisible editor's glyph positions.
                Box(Modifier.matchParentSize().pointerInput(enabled, codeLength, direction) {
                    if (enabled) detectTapGestures { position ->
                        val gap = 8.dp.toPx()
                        val cellWidth = (size.width - gap * (codeLength - 1)) / codeLength
                        val x = if (direction == LayoutDirection.Rtl) size.width - position.x else position.x
                        val index = (x / (cellWidth + gap)).toInt().coerceIn(0, codeLength - 1)
                            .coerceAtMost(editor.fieldValue.text.length)
                        editor.focusRequester.requestFocus()
                        editor.fieldValue = editor.fieldValue.copy(
                            selection = if (index < editor.fieldValue.text.length) TextRange(index, index + 1) else TextRange(index),
                            composition = null,
                        )
                        keyboard?.show()
                    }
                })
            }
        },
    )
}

@Composable
internal fun CodeInputCells(value: String, selection: TextRange, codeLength: Int, focused: Boolean, isError: Boolean = false) {
    val activeIndex = selection.min.coerceIn(0, codeLength - 1)
    Row(Modifier.fillMaxWidth().clearAndSetSemantics {}, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(codeLength) { index ->
            val active = focused && index == activeIndex
            Surface(modifier = Modifier.weight(1f).heightIn(min = 50.dp),
                color = when {
                    isError -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
                    active -> MaterialTheme.colorScheme.primaryContainer
                    else -> MaterialTheme.colorScheme.surfaceContainer
                },
                border = when {
                    active -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                    isError -> BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
                    else -> null
                },
                shape = MaterialTheme.shapes.small) {
                Box(Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                    Text(value.getOrNull(index)?.toString() ?: "",
                        color = if (active && !isError) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                    if (active && selection.collapsed) Box(
                        Modifier.align(if (index < selection.start) Alignment.CenterEnd else if (index < value.length) Alignment.CenterStart else Alignment.Center)
                            .padding(horizontal = 5.dp).width(2.dp).height(24.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
internal fun CodePasteButton(editor: CodeEditor, onValueChange: (String) -> Unit, label: String, codeLength: Int, enabled: Boolean) {
    val clipboard = LocalClipboardManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    TextButton(enabled = enabled, shape = MaterialTheme.shapes.small, onClick = {
        if (enabled) {
            clipboard.getText()?.text?.let { raw ->
                val text = normalizeOneTimeCode(raw, codeLength)
                editor.fieldValue = TextFieldValue(text, TextRange(text.length))
                onValueChange(text)
            }
            editor.focusRequester.requestFocus()
            keyboard?.show()
        }
    }) { Text(label) }
}
