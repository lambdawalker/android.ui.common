package com.apexfission.android.ui.common.sample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.apexfission.android.ui.common.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { SampleTheme { ComponentCatalog() } }
    }
}

@Composable
internal fun SampleTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(), content = content)
}

/** Offline stateful demo; callbacks simulate UI state only, never server verification. */
@Composable
internal fun ComponentCatalog() {
    var code by rememberSaveable { mutableStateOf("") }
    var mode by rememberSaveable { mutableIntStateOf(0) }
    var clicked by rememberSaveable { mutableStateOf(false) }
    val modeLabel = stringResource(listOf(R.string.mode_normal, R.string.mode_error, R.string.mode_busy, R.string.mode_disabled)[mode])
    CatalogScreen(title = stringResource(R.string.app_name), description = stringResource(R.string.catalog_description)) {
        item {
            CodeVerificationCard(
                value = code, onValueChange = { code = it },
                codeLabel = stringResource(R.string.code_label),
                codeContentDescription = stringResource(R.string.code_description),
                pasteLabel = stringResource(R.string.paste),
                instructions = stringResource(R.string.instructions), recipient = "name@example.invalid",
                enabled = mode != 3,
                errorMessage = if (mode == 1) stringResource(R.string.error) else null,
                busyMessage = if (mode == 2) stringResource(R.string.busy) else null,
                actions = {
                    Button(onClick = { mode = 1 }, enabled = mode != 2 && mode != 3 && code.length == 6,
                        shape = MaterialTheme.shapes.small) { Text(stringResource(R.string.verify)) }
                    TextButton(onClick = { code = ""; mode = 0 }, shape = MaterialTheme.shapes.small) {
                        Text(stringResource(R.string.reset))
                    }
                },
            )
        }
        item {
            Text(stringResource(R.string.state_label, modeLabel))
            OutlinedButton(onClick = { mode = (mode + 1) % 4 }, shape = MaterialTheme.shapes.small) {
                Text(stringResource(R.string.next_state))
            }
        }
        item {
            ActionCard(stringResource(R.string.action_title), stringResource(R.string.action_body),
                stringResource(if (clicked) R.string.clicked else R.string.open), { clicked = !clicked })
        }
        item { ContentCard {
            SectionHeading(stringResource(R.string.content_title))
            Text(stringResource(R.string.content_body))
        } }
        item { EmptyStateCard(stringResource(R.string.empty_title), stringResource(R.string.empty_body)) }
    }
}

@Preview(name = "sample-light", widthDp = 393, heightDp = 900)
@Preview(name = "sample-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "sample-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
internal fun ComponentCatalogPreview() { SampleTheme { ComponentCatalog() } }
