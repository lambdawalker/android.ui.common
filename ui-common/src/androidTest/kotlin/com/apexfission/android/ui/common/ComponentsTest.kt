package com.apexfission.android.ui.common

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
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
    @Test fun tappingCellsReplacesDigitsAndMovesTheVisibleSelection() {
        var code by mutableStateOf("123456")
        var density = 1f
        val primary = Color(0xFF2368BC)
        compose.setContent { MaterialTheme(colorScheme = lightColorScheme(primary = primary)) {
            density = LocalDensity.current.density
            OneTimeCodeField(code, { code = it }, "Code")
        } }
        val field = compose.onNodeWithContentDescription("Code")
        compose.onAllNodes(hasSetTextAction()).assertCountEquals(1)
        field.performTouchInput { click(Offset(width * 2.5f / 6, height / 2f)) }
        field.assertIsFocused().assert(SemanticsMatcher.expectValue(SemanticsProperties.TextSelectionRange, TextRange(2, 3)))
        assertActiveCell(field, 2, density, primary)
        field.performTextInput("9")
        compose.runOnIdle { assertEquals("129456", code) }
        field.assert(SemanticsMatcher.expectValue(SemanticsProperties.TextSelectionRange, TextRange(3, 4)))
        assertActiveCell(field, 3, density, primary)
        field.performTextInput("8")
        compose.runOnIdle { assertEquals("129856", code) }
        assertActiveCell(field, 4, density, primary)
        field.performKeyInput { pressKey(Key.Backspace) }
        compose.runOnIdle { assertEquals("12986", code) }
        field.assert(SemanticsMatcher.expectValue(SemanticsProperties.TextSelectionRange, TextRange(4)))
        assertActiveCell(field, 4, density, primary)
    }

    @Test fun tappingAnEmptyCellTargetsTheNextDigitAndTypingAdvancesTheOutline() {
        var code by mutableStateOf("12")
        var density = 1f
        val primary = Color(0xFF2368BC)
        compose.setContent { MaterialTheme(colorScheme = lightColorScheme(primary = primary)) {
            density = LocalDensity.current.density
            OneTimeCodeField(code, { code = it }, "Code")
        } }
        val field = compose.onNodeWithContentDescription("Code")
        field.performTouchInput { click(Offset(width * 5.5f / 6, height / 2f)) }
        field.assert(SemanticsMatcher.expectValue(SemanticsProperties.TextSelectionRange, TextRange(2)))
        assertActiveCell(field, 2, density, primary)
        field.performTextInput("3")
        compose.runOnIdle { assertEquals("123", code) }
        assertActiveCell(field, 3, density, primary)
        compose.runOnIdle { code = "" }
        field.assert(SemanticsMatcher.expectValue(SemanticsProperties.TextSelectionRange, TextRange.Zero))
        assertActiveCell(field, 0, density, primary)
    }

    @Suppress("DEPRECATION")
    @Test fun cardActionsSitBelowRecipientAndPasteKeepsTheCodeEditable() {
        var code by mutableStateOf("")
        var changes = 0
        compose.setContent { MaterialTheme {
            val clipboard = LocalClipboardManager.current
            SideEffect { clipboard.setText(AnnotatedString("12 34-56")) }
            CodeVerificationCard(
                details = VerificationCardDetails(recipientStatus = "Awaiting confirmation"),
                value = code, onValueChange = { code = it }, codeLabel = "Confirmation code",
                codeContentDescription = "Code", pasteLabel = "Paste code", recipient = "name@example.invalid",
                actions = { TextButton(onClick = { changes++ }) { Text("Change destination") } },
            )
        } }
        val recipient = compose.onNodeWithText("Awaiting confirmation").fetchSemanticsNode().boundsInRoot
        val paste = compose.onNodeWithText("Paste code")
        val change = compose.onNodeWithText("Change destination")
        assertTrue(paste.fetchSemanticsNode().boundsInRoot.top > recipient.bottom)
        assertTrue(change.fetchSemanticsNode().boundsInRoot.top > recipient.bottom)
        compose.runOnIdle { assertEquals("", code) }
        paste.performClick()
        compose.runOnIdle { assertEquals("123456", code) }
        compose.onNodeWithContentDescription("Code").assertIsFocused()
        change.performClick()
        compose.runOnIdle { assertEquals(1, changes) }
    }

    private fun assertActiveCell(field: SemanticsNodeInteraction, index: Int, density: Float, primary: Color) {
        val pixels = field.captureToImage().toPixelMap()
        val gap = 8 * density
        val width = (pixels.width - 5 * gap) / 6
        for (cell in 0..5) {
            val x = (cell * (width + gap) + density).toInt()
            val border = pixels[x, pixels.height / 2].toArgb()
            if (cell == index) assertEquals("Active cell ${cell + 1} has the host-primary border", primary.toArgb(), border)
            else assertTrue("Cell ${cell + 1} is not highlighted", border != primary.toArgb())
        }
    }

}
