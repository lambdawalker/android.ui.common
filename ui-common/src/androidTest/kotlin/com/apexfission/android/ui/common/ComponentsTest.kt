package com.apexfission.android.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ComponentsTest {
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

    @Test fun codeUsesOneEditableNodeAndSupportsTypingReplacementAndDeletion() {
        var code by mutableStateOf("")
        compose.setContent { MaterialTheme { OneTimeCodeField(code, { code = it }, "Code") } }
        compose.onAllNodes(hasSetTextAction()).assertCountEquals(1)
        val field = compose.onNodeWithContentDescription("Code")
        field.performTextInput("12 34-56 7")
        compose.runOnIdle { assertEquals("123456", code) }
        field.performTextReplacement("12356")
        compose.runOnIdle { assertEquals("12356", code) }
        field.performTextClearance()
        compose.runOnIdle { assertEquals("", code) }
    }

    @Suppress("DEPRECATION")
    @Test fun pasteIsExplicitAndRespectsLength() {
        var code by mutableStateOf("")
        compose.setContent { MaterialTheme {
            val clipboard = LocalClipboardManager.current
            SideEffect { clipboard.setText(AnnotatedString("12 34-56")) }
            OneTimeCodeField(code, { code = it }, "Code", codeLength = 4, pasteLabel = "Paste")
        } }
        compose.runOnIdle { assertEquals("", code) }
        compose.onNodeWithText("Paste").performClick()
        compose.runOnIdle { assertEquals("1234", code) }
    }

    @Test fun disabledFieldAndPasteAreDisabled() {
        compose.setContent { MaterialTheme {
            OneTimeCodeField("12", {}, "Code", enabled = false, pasteLabel = "Paste")
        } }
        compose.onNodeWithContentDescription("Code").assertIsNotEnabled()
        compose.onNodeWithText("Paste").assertIsNotEnabled()
    }

    @Test fun verificationCardDisplaysHostMessagesAndActions() {
        var clicks = 0
        compose.setContent { MaterialTheme {
            CodeVerificationCard("123", {}, "Confirmation code", "Code", "Paste",
                instructions = "Use your latest code", recipient = "name@example.invalid",
                errorMessage = "Try again", supportingContent = { Text("Two attempts left") },
                actions = { TextButton(onClick = { clicks++ }) { Text("Change address") } })
        } }
        compose.onNodeWithText("Try again").assertIsDisplayed()
        compose.onNodeWithText("Two attempts left").assertIsDisplayed()
        compose.onNodeWithText("Change address").performClick()
        compose.runOnIdle { assertEquals(1, clicks) }
    }

    @Test fun busyCardDisablesInputUntilHostClearsBusyState() {
        var busy by mutableStateOf(true)
        compose.setContent { MaterialTheme {
            CodeVerificationCard("123", {}, "Confirmation code", "Code", "Paste",
                busyMessage = if (busy) "Checking" else null)
        } }
        compose.onNodeWithContentDescription("Code").assertIsNotEnabled()
        compose.onNodeWithText("Paste").assertIsNotEnabled()
        compose.onNodeWithText("Checking").assertIsDisplayed()
        compose.runOnIdle { busy = false }
        compose.onNodeWithContentDescription("Code").assertIsEnabled()
        compose.onNodeWithText("Paste").assertIsEnabled()
    }

    @Test fun detailedCardKeepsOneFieldAndHostOwnedRecipientStatus() {
        var code by mutableStateOf("")
        var status by mutableStateOf("Awaiting confirmation")
        compose.setContent { MaterialTheme {
            CodeVerificationCard(
                details = VerificationCardDetails("6 digits", "Confirm access to continue.", status),
                value = code, onValueChange = { code = it }, codeLabel = "Confirmation code",
                codeContentDescription = "Code", pasteLabel = null,
                recipient = "name@example.invalid",
            )
        } }
        compose.onAllNodes(hasSetTextAction()).assertCountEquals(1)
        compose.onNodeWithText("6 digits").assertIsDisplayed()
        compose.onNodeWithText("name@example.invalid").assertIsDisplayed()
        compose.onNodeWithText("Awaiting confirmation").assertIsDisplayed()
        compose.onNodeWithContentDescription("Code").performTextInput("123456")
        compose.onNodeWithText("Awaiting confirmation").assertIsDisplayed()
        compose.runOnIdle { assertEquals("123456", code); status = "Host supplied status" }
        compose.onNodeWithText("Host supplied status").assertIsDisplayed()
    }
}
