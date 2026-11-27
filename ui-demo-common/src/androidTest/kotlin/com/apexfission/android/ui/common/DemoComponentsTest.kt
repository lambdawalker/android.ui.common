package com.apexfission.android.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class DemoComponentsTest {
    @get:Rule val compose = createComposeRule()

    @Test fun actionCardInvokesOnlySelectedAction() {
        var clicks = 0
        compose.setContent { MaterialTheme { ActionCard("Title", "Body", "Open", { clicks++ }) } }
        compose.onNodeWithText("Open").performClick()
        compose.runOnIdle { assertEquals(1, clicks) }
    }

    @Test fun catalogSupportsConsumerHeaderAndEmptyState() {
        var clicks = 0
        compose.setContent { MaterialTheme {
            CatalogScreen(title = "Catalog", description = "Hidden instruction", showEmptyState = true,
                headerActions = { TextButton(onClick = { clicks++ }) { Text("Settings") } },
                emptyState = { EmptyStateCard("Nothing yet", "Come back later") })
        } }
        compose.onNodeWithText("Nothing yet").assertIsDisplayed()
        compose.onNodeWithText("Hidden instruction").assertDoesNotExist()
        compose.onNodeWithText("Settings").performClick()
        compose.runOnIdle { assertEquals(1, clicks) }
    }

}
