package com.apexfission.android.ui.common.sample

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(name = "sample-light", widthDp = 393, heightDp = 900)
@Preview(name = "sample-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "sample-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun SampleScreenshots() { SampleTheme { ComponentCatalog() } }
