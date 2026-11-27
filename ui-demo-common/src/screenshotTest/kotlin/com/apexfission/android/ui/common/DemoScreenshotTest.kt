package com.apexfission.android.ui.common

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(name = "components-light", widthDp = 393)
@Preview(name = "components-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ComponentsScreenshot() { ComponentsPreview() }

@PreviewTest
@Preview(name = "catalog-light", widthDp = 393, heightDp = 700)
@Preview(name = "catalog-dark", widthDp = 393, heightDp = 700, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CatalogScreenshot() { CatalogPreview() }

@PreviewTest
@Preview(name = "empty-catalog-light", widthDp = 393, heightDp = 700)
@Preview(name = "empty-catalog-dark", widthDp = 393, heightDp = 700, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyCatalogScreenshot() { DemoPreviewHost {
    CatalogScreen("Catalog", "Hidden instruction", showEmptyState = true,
        emptyState = { EmptyStateCard("No items", "Your items will appear here.") })
} }
