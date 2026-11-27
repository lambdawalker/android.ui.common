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

@PreviewTest
@Preview(name = "verification-light", widthDp = 393, heightDp = 900)
@Preview(name = "verification-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "verification-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun VerificationDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.Verification) } }

@PreviewTest
@Preview(name = "codeinput-light", widthDp = 393, heightDp = 900)
@Preview(name = "codeinput-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "codeinput-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun CodeInputDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.CodeInput) } }

@PreviewTest
@Preview(name = "action-light", widthDp = 393, heightDp = 900)
@Preview(name = "action-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "action-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun ActionDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.Action) } }

@PreviewTest
@Preview(name = "content-light", widthDp = 393, heightDp = 900)
@Preview(name = "content-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "content-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun ContentDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.Content) } }

@PreviewTest
@Preview(name = "recipient-light", widthDp = 393, heightDp = 900)
@Preview(name = "recipient-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "recipient-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun RecipientDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.Recipient) } }

@PreviewTest
@Preview(name = "emptystate-light", widthDp = 393, heightDp = 900)
@Preview(name = "emptystate-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "emptystate-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
fun EmptyStateDemoScreenshots() { SampleTheme { ComponentDemoScreen(ComponentDemo.EmptyState) } }
