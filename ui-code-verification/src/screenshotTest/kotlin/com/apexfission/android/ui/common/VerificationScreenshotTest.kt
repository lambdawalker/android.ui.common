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
fun EmptyVerificationScreenshot() { VerificationPreviewHost { VerificationFixture() } }

@PreviewTest
@Preview(name = "partial-light", widthDp = 393)
@Preview(name = "partial-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PartialVerificationScreenshot() { VerificationPreviewHost { VerificationFixture("123") } }

@PreviewTest
@Preview(name = "complete-light", widthDp = 393)
@Preview(name = "complete-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CompleteVerificationScreenshot() { VerificationPreviewHost { VerificationFixture("123456") } }

@PreviewTest
@Preview(name = "error-light", widthDp = 393)
@Preview(name = "error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorVerificationScreenshot() { VerificationPreviewHost { VerificationFixture("123456", error = true) } }

@PreviewTest
@Preview(name = "busy-light", widthDp = 393)
@Preview(name = "busy-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BusyVerificationScreenshot() { VerificationPreviewHost { VerificationFixture("123456", busy = true) } }

@PreviewTest
@Preview(name = "disabled-light", widthDp = 393)
@Preview(name = "disabled-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DisabledVerificationScreenshot() { VerificationPreviewHost { VerificationFixture("12", enabled = false) } }

@PreviewTest
@Preview(name = "host-theme-narrow-large", widthDp = 320, fontScale = 1.6f)
@Composable
fun AlternateHostScreenshot() { VerificationPreviewHost(alternate = true) { VerificationFixture("123456", error = true) } }

@PreviewTest
@Preview(name = "detailed-light", widthDp = 393)
@Preview(name = "detailed-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "detailed-narrow-large", widthDp = 320, fontScale = 1.6f)
@Preview(name = "detailed-narrow-large-dark", widthDp = 320, fontScale = 1.6f, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailedVerificationScreenshot() { VerificationPreviewHost { DetailedVerificationFixture() } }

@PreviewTest
@Preview(name = "focus-next-light", widthDp = 393)
@Preview(name = "focus-next-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NextDigitFocusScreenshot() { VerificationPreviewHost { CodeInputCells("123", TextRange(3), 6, true) } }

@PreviewTest
@Preview(name = "focus-selected-light", widthDp = 393)
@Preview(name = "focus-selected-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectedDigitFocusScreenshot() { VerificationPreviewHost { CodeInputCells("123456", TextRange(2, 3), 6, true) } }

@PreviewTest
@Preview(name = "focus-end-light", widthDp = 393)
@Preview(name = "focus-end-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CompleteCodeFocusScreenshot() { VerificationPreviewHost { CodeInputCells("123456", TextRange(6), 6, true) } }

@PreviewTest
@Preview(name = "detailed-error-light", widthDp = 393)
@Preview(name = "detailed-error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "detailed-error-narrow-large", widthDp = 320, fontScale = 1.6f)
@Preview(name = "detailed-error-narrow-large-dark", widthDp = 320, fontScale = 1.6f, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailedErrorScreenshot() { VerificationPreviewHost { DetailedVerificationFixture(error = true) } }

@PreviewTest
@Preview(name = "focus-error-light", widthDp = 393)
@Preview(name = "focus-error-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorFocusScreenshot() { VerificationPreviewHost { CodeInputCells("482019", TextRange(2, 3), 6, true, isError = true) } }
