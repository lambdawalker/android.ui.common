// Extracted and adapted from android.apexfission.auth; owner licensed Apache-2.0.
package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/** Caller-resolved supplementary copy. Status is presentation, never proof of verification. */
public data class VerificationCardDetails(
    val codeLengthLabel: String? = null,
    val explanation: String? = null,
    val recipientStatus: String? = null,
)

/**
 * Presentation-only verification card. All wording and actions come from the host.
 * [enabled] controls the field/paste; hosts must apply their enabled state to [actions].
 * [supportingContent] can display resend timing/attempts without transferring their ownership.
 */
@Composable
public fun CodeVerificationCard(
    value: String,
    onValueChange: (String) -> Unit,
    codeLabel: String,
    codeContentDescription: String,
    pasteLabel: String?,
    modifier: Modifier = Modifier,
    instructions: String? = null,
    recipient: String? = null,
    codeLength: Int = 6,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    autofillContentType: ContentType? = null,
    busyMessage: String? = null,
    errorMessage: String? = null,
    supportingContent: (@Composable ColumnScope.() -> Unit)? = null,
    actions: (@Composable ColumnScope.() -> Unit)? = null,
) {
    CodeVerificationCard(
        details = VerificationCardDetails(), value = value, onValueChange = onValueChange,
        codeLabel = codeLabel, codeContentDescription = codeContentDescription, pasteLabel = pasteLabel,
        modifier = modifier, instructions = instructions, recipient = recipient, codeLength = codeLength,
        enabled = enabled, autoFocus = autoFocus, autofillContentType = autofillContentType,
        busyMessage = busyMessage, errorMessage = errorMessage,
        supportingContent = supportingContent, actions = actions,
    )
}

/**
 * Verification card with a code legend, explanation and recipient summary.
 * The original overload is retained, including its trailing [actions] lambda.
 * Icons are decorative slots; [details] supplies the accessible status text.
 */
@Composable
public fun CodeVerificationCard(
    details: VerificationCardDetails,
    value: String,
    onValueChange: (String) -> Unit,
    codeLabel: String,
    codeContentDescription: String,
    pasteLabel: String?,
    modifier: Modifier = Modifier,
    instructions: String? = null,
    recipient: String? = null,
    codeLength: Int = 6,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    autofillContentType: ContentType? = null,
    busyMessage: String? = null,
    errorMessage: String? = null,
    recipientLeadingIcon: (@Composable () -> Unit)? = null,
    recipientTrailingIcon: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable ColumnScope.() -> Unit)? = null,
    actions: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val editor = rememberCodeEditor(value)
    val inputEnabled = enabled && busyMessage == null
    Card(modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (!instructions.isNullOrBlank()) Text(instructions, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CodeInput(editor, onValueChange, codeContentDescription, codeLength, inputEnabled, autoFocus, autofillContentType)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top) {
                    Text(codeLabel, Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
                    details.codeLengthLabel?.let {
                        Text(it, style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            details.explanation?.let { Text(it, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant) }
            if (!recipient.isNullOrBlank()) VerificationRecipientRow(
                recipient, details.recipientStatus, leadingIcon = recipientLeadingIcon,
                trailingIcon = recipientTrailingIcon,
            )
            if (busyMessage != null) Text(busyMessage, style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
            if (errorMessage != null) Text(errorMessage, color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
            supportingContent?.invoke(this)
            if (pasteLabel != null || actions != null) Column(Modifier.fillMaxWidth()) {
                if (pasteLabel != null) CodePasteButton(editor, onValueChange, pasteLabel, codeLength, inputEnabled)
                actions?.invoke(this)
            }
        }
    }
}

/** A wrapping recipient/status summary; decorative icons must not duplicate the status label. */
@Composable
public fun VerificationRecipientRow(
    recipient: String,
    status: String? = null,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Surface(modifier.fillMaxWidth(), shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainer, contentColor = MaterialTheme.colorScheme.onSurface) {
        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            leadingIcon?.invoke()
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(recipient, style = MaterialTheme.typography.titleSmall)
                status?.let { Text(it, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }) }
            }
            trailingIcon?.invoke()
        }
    }
}
