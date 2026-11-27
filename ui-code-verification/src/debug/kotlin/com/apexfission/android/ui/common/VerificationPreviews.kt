package com.apexfission.android.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily

@Composable
internal fun VerificationPreviewHost(alternate: Boolean = false, content: @Composable () -> Unit) {
    val scheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    val typography = Typography()
    MaterialTheme(typography = if (alternate) typography.copy(bodyLarge = typography.bodyLarge.copy(fontFamily = FontFamily.Serif)) else typography,
        colorScheme = if (alternate) scheme.copy(primary = Color(0xFF806000)) else scheme,
        shapes = if (alternate) Shapes(small = RoundedCornerShape(0.dp)) else Shapes()) {
        Surface { content() }
    }
}

@Composable
internal fun VerificationFixture(code: String = "", error: Boolean = false, busy: Boolean = false, enabled: Boolean = true) {
    CodeVerificationCard(code, {}, "Confirmation code", "Six digit code", "Paste code",
        instructions = "Open the link or enter your latest code.", recipient = "name@example.invalid",
        enabled = enabled, errorMessage = if (error) "Check the code and try again." else null,
        busyMessage = if (busy) "Verifying…" else null,
        supportingContent = { Text("You can request another message in a minute.") },
        actions = { TextButton({}, enabled = enabled && !busy, shape = MaterialTheme.shapes.small) { Text("Change destination") } })
}

@Composable
internal fun DetailedVerificationFixture(error: Boolean = false) {
    CodeVerificationCard(
        details = VerificationCardDetails(if (error) "Incorrect code" else "6 digits", "Confirm access to this email address to continue.", "Awaiting confirmation"),
        value = if (error) "482019" else "", onValueChange = {}, codeLabel = "Confirmation code", codeContentDescription = "Six digit code",
        pasteLabel = "Paste code", instructions = "Enter the 6-digit code sent to your email to continue.",
        recipient = "alex.turner@example.invalid",
        errorMessage = if (error) "That code didn’t match. Check the six-digit code in your email and try again." else null,
        actions = { TextButton({}) { Text("Change destination") } },
    )
}

@Preview(name = "verification-light", widthDp = 393)
@Preview(name = "verification-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun VerificationPreview() { VerificationPreviewHost { VerificationFixture("123") } }
