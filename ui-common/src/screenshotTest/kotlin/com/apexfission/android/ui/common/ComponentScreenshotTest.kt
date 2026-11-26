package com.apexfission.android.ui.common

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(name = "empty-light", widthDp = 393)
@Preview(name = "empty-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyVerificationScreenshot() { PreviewHost { VerificationFixture() } }

@PreviewTest
@Preview(name = "partial-light", widthDp = 393)
@Preview(name = "partial-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PartialVerificationScreenshot() { PreviewHost { VerificationFixture("123") } }

@PreviewTest
@Preview(name = "complete-light", widthDp = 393)
@Preview(name = "complete-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CompleteVerificationScreenshot() { PreviewHost { VerificationFixture("123456") } }

@PreviewTest
@Preview(name = "error-light", widthDp = 393)
@Preview(name = "error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorVerificationScreenshot() { PreviewHost { VerificationFixture("123456", error = true) } }

@PreviewTest
@Preview(name = "busy-light", widthDp = 393)
@Preview(name = "busy-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BusyVerificationScreenshot() { PreviewHost { VerificationFixture("123456", busy = true) } }

@PreviewTest
@Preview(name = "disabled-light", widthDp = 393)
@Preview(name = "disabled-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DisabledVerificationScreenshot() { PreviewHost { VerificationFixture("12", enabled = false) } }

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
@Preview(name = "host-theme-narrow-large", widthDp = 320, fontScale = 1.6f)
@Composable
fun AlternateHostScreenshot() { PreviewHost(alternate = true) { VerificationFixture("123456", error = true) } }

@PreviewTest
@Preview(name = "empty-catalog-light", widthDp = 393, heightDp = 700)
@Preview(name = "empty-catalog-dark", widthDp = 393, heightDp = 700, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyCatalogScreenshot() { PreviewHost {
    CatalogScreen("Catalog", "Hidden instruction", showEmptyState = true,
        emptyState = { EmptyStateCard("No items", "Your items will appear here.") })
} }

@PreviewTest
@Preview(name = "detailed-light", widthDp = 393)
@Preview(name = "detailed-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "detailed-narrow-large", widthDp = 320, fontScale = 1.6f)
@Preview(name = "detailed-narrow-large-dark", widthDp = 320, fontScale = 1.6f, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailedVerificationScreenshot() { PreviewHost { DetailedVerificationFixture() } }

@PreviewTest
@Preview(name = "focus-next-light", widthDp = 393)
@Preview(name = "focus-next-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NextDigitFocusScreenshot() { PreviewHost { CodeInputCells("123", TextRange(3), 6, true) } }

@PreviewTest
@Preview(name = "focus-selected-light", widthDp = 393)
@Preview(name = "focus-selected-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectedDigitFocusScreenshot() { PreviewHost { CodeInputCells("123456", TextRange(2, 3), 6, true) } }

@PreviewTest
@Preview(name = "focus-end-light", widthDp = 393)
@Preview(name = "focus-end-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CompleteCodeFocusScreenshot() { PreviewHost { CodeInputCells("123456", TextRange(6), 6, true) } }

@PreviewTest
@Preview(name = "detailed-error-light", widthDp = 393)
@Preview(name = "detailed-error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "detailed-error-narrow-large", widthDp = 320, fontScale = 1.6f)
@Preview(name = "detailed-error-narrow-large-dark", widthDp = 320, fontScale = 1.6f, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailedErrorScreenshot() { PreviewHost { DetailedVerificationFixture(error = true) } }

@PreviewTest
@Preview(name = "focus-error-light", widthDp = 393)
@Preview(name = "focus-error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorFocusScreenshot() { PreviewHost { CodeInputCells("482019", TextRange(2, 3), 6, true, isError = true) } }
