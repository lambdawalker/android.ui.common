// Extracted and adapted from android.apexfission.demo-common (Apache-2.0).
package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Themed empty state; the host supplies localized wording and optional actions. */
@Composable
public fun EmptyStateCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actions: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Surface(modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer) {
        Column(Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            Text(description, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            actions?.invoke(this)
        }
    }
}
