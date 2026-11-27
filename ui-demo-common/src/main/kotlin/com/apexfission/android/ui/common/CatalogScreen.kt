// Extracted and adapted from android.apexfission.demo-common (Apache-2.0).
package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Centered lazy catalog with host-supplied content, header actions and empty state.
 * Consumes scaffold insets once and combines them with [contentPadding].
 * [description] is hidden when [showEmptyState] is true.
 */
@Composable
public fun CatalogScreen(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    maxContentWidth: Dp = 640.dp,
    contentPadding: PaddingValues = PaddingValues(24.dp),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    itemSpacing: Dp = 16.dp,
    showEmptyState: Boolean = false,
    headerActions: (@Composable () -> Unit)? = null,
    emptyState: (@Composable () -> Unit)? = null,
    content: LazyListScope.() -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = contentWindowInsets,
    ) { insets ->
        val layoutDirection = LocalLayoutDirection.current
        val combinedPadding = PaddingValues(
            start = insets.calculateStartPadding(layoutDirection) + contentPadding.calculateStartPadding(layoutDirection),
            top = insets.calculateTopPadding() + contentPadding.calculateTopPadding(),
            end = insets.calculateEndPadding(layoutDirection) + contentPadding.calculateEndPadding(layoutDirection),
            bottom = insets.calculateBottomPadding() + contentPadding.calculateBottomPadding(),
        )
        Box(
            modifier = Modifier.fillMaxSize().consumeWindowInsets(insets),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = Modifier.widthIn(max = maxContentWidth).fillMaxSize(),
                contentPadding = combinedPadding,
                verticalArrangement = Arrangement.spacedBy(itemSpacing),
            ) {
                item(key = "ui_common_catalog_header", contentType = "header") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        eyebrow?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Text(
                            text = title,
                            modifier = Modifier.semantics { heading() },
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        if (!showEmptyState) {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
                if (headerActions != null) {
                    item(key = "ui_common_catalog_actions", contentType = "actions") { headerActions() }
                }
                content()
                if (showEmptyState) {
                    item(key = "ui_common_catalog_empty", contentType = "empty") { emptyState?.invoke() }
                }
            }
        }
    }
}
