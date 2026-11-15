// Extracted and adapted from android.apexfission.auth; owner licensed Apache-2.0.
package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

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
    Card(modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
        Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (!instructions.isNullOrBlank()) Text(instructions, style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (!recipient.isNullOrBlank()) Text(recipient, style = MaterialTheme.typography.titleSmall)
            Text(codeLabel, style = MaterialTheme.typography.titleSmall)
            OneTimeCodeField(value, onValueChange, codeContentDescription, codeLength = codeLength,
                enabled = enabled && busyMessage == null, autoFocus = autoFocus, pasteLabel = pasteLabel,
                autofillContentType = autofillContentType)
            if (busyMessage != null) Text(busyMessage, style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
            if (errorMessage != null) Text(errorMessage, color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
            supportingContent?.invoke(this)
            actions?.invoke(this)
        }
    }
}
