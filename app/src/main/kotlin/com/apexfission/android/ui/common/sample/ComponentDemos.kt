package com.apexfission.android.ui.common.sample

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apexfission.android.ui.common.*

internal enum class ComponentDemo(@param:StringRes val title: Int, @param:StringRes val description: Int) {
    Verification(R.string.verification, R.string.verification_demo_description),
    CodeInput(R.string.code_input_demo, R.string.code_input_demo_description),
    Action(R.string.action_title, R.string.action_body),
    Content(R.string.content_demo, R.string.content_body),
    Recipient(R.string.recipient_demo, R.string.recipient_demo_description),
    EmptyState(R.string.empty_demo, R.string.empty_body),
}

/** Navigation and example state belong to the unpublished sample, never the library. */
@Composable
internal fun ComponentDemoApp(onOpenSettings: () -> Unit) {
    var component by rememberSaveable { mutableStateOf<ComponentDemo?>(null) }
    var selected by rememberSaveable { mutableStateOf<ComponentDemo?>(null) }
    val catalogState = rememberSaveableStateHolder()
    BackHandler(enabled = component != null) { if (selected != null) selected = null else component = null }
    val demo = selected
    if (component == null) {
        catalogState.SaveableStateProvider("catalog") {
            ComponentCatalog(onOpenDemo = { component = it }, onOpenSettings = onOpenSettings)
        }
    } else if (demo == null) {
        catalogState.SaveableStateProvider("demos-${component!!.name}") {
            ComponentDemoList(component!!, onOpenDemo = { selected = component },
                onBack = { component = null }, onOpenSettings = onOpenSettings)
        }
    } else key(demo) {
        ComponentDemoScreen(demo, onBack = { selected = null }, onOpenSettings = onOpenSettings)
    }
}

@Composable
internal fun ComponentCatalog(onOpenDemo: (ComponentDemo) -> Unit = {}, onOpenSettings: () -> Unit = {}) {
    CatalogScreen(title = stringResource(R.string.app_name), description = stringResource(R.string.catalog_description),
        headerActions = { DemoHeaderActions(onOpenSettings) }) {
        items(ComponentDemo.entries, key = { it.name }) { demo ->
            Card(onClick = { onOpenDemo(demo) }, modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(stringResource(demo.title), style = MaterialTheme.typography.titleMedium)
                        Text(stringResource(demo.description), style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(painterResource(R.drawable.demo_chevron), contentDescription = null,
                        modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

/** Every component owns its demo catalog; add scenarios here as components grow. */
@Composable
internal fun ComponentDemoList(component: ComponentDemo, onOpenDemo: () -> Unit = {},
    onBack: () -> Unit = {}, onOpenSettings: () -> Unit = {}) {
    DemoSectionScreen(title = stringResource(component.title), onBack = onBack,
        backLabel = R.string.back_to_components) {
        item {
            ActionCard(stringResource(R.string.interactive_demo), stringResource(component.description),
                stringResource(R.string.open), onOpenDemo)
        }
    }
}

@Composable
private fun DemoHeaderActions(onOpenSettings: () -> Unit, onBack: (() -> Unit)? = null, @StringRes backLabel: Int = R.string.back_to_demos) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (onBack != null) TextButton(onClick = onBack, shape = MaterialTheme.shapes.small) {
            Icon(painterResource(R.drawable.demo_back), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(backLabel))
        }
        OutlinedButton(onClick = onOpenSettings, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.small) {
            Text(stringResource(R.string.app_settings))
        }
    }
}

/** Sample-only navigation chrome; content scrolls independently of the fixed controls. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoSectionScreen(title: String, onBack: () -> Unit,
    @StringRes backLabel: Int = R.string.back_to_demos,
    bottomBar: @Composable () -> Unit = {}, content: LazyListScope.() -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(title) }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(painterResource(R.drawable.demo_back), contentDescription = stringResource(backLabel))
                }
            })
        },
        bottomBar = bottomBar,
    ) { insets ->
        LazyColumn(Modifier.fillMaxSize().padding(insets).consumeWindowInsets(insets).imePadding(),
            contentPadding = PaddingValues(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp),
            content = content)
    }
}

/** Each screen is an offline, caller-owned example with a fresh state when reopened. */
@Composable
internal fun ComponentDemoScreen(demo: ComponentDemo, onBack: () -> Unit = {}, onOpenSettings: () -> Unit = {}) {
    var code by rememberSaveable { mutableStateOf("") }
    var mode by rememberSaveable { mutableIntStateOf(0) }
    var clicked by rememberSaveable { mutableStateOf(false) }
    val modeLabel = stringResource(listOf(R.string.mode_normal, R.string.mode_error, R.string.mode_busy, R.string.mode_disabled)[mode])
    DemoSectionScreen(title = stringResource(demo.title), onBack = onBack,
        bottomBar = {
            if (demo == ComponentDemo.Verification) Surface(tonalElevation = 3.dp) {
                Row(Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(modeLabel, modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
                    OutlinedButton(onClick = { mode = (mode + 1) % 4 }, shape = MaterialTheme.shapes.small) {
                        Text(stringResource(R.string.next_state))
                    }
                }
            }
        }) {
        when (demo) {
            ComponentDemo.Verification -> {
                item {
                    CodeVerificationCard(
                        details = VerificationCardDetails(stringResource(if (mode == 1) R.string.incorrect_code else R.string.code_length),
                            stringResource(R.string.explanation), stringResource(R.string.recipient_status)),
                        value = code, onValueChange = { code = it; if (mode == 1) mode = 0 },
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
            }
            ComponentDemo.CodeInput -> item {
                ContentCard {
                    SectionHeading(stringResource(R.string.code_label))
                    OneTimeCodeField(code, { code = it }, stringResource(R.string.code_description),
                        pasteLabel = stringResource(R.string.paste))
                    TextButton(onClick = { code = "" }, shape = MaterialTheme.shapes.small) { Text(stringResource(R.string.reset)) }
                }
            }
            ComponentDemo.Action -> item {
                ActionCard(stringResource(R.string.action_title), stringResource(R.string.action_body),
                    stringResource(if (clicked) R.string.clicked else R.string.open), { clicked = !clicked })
            }
            ComponentDemo.Content -> item {
                ContentCard {
                    SectionHeading(stringResource(R.string.content_title))
                    Text(stringResource(R.string.content_body))
                }
            }
            ComponentDemo.Recipient -> item {
                VerificationRecipientRow("name@example.invalid", stringResource(R.string.recipient_status))
            }
            ComponentDemo.EmptyState -> item {
                EmptyStateCard(stringResource(R.string.empty_title), stringResource(R.string.empty_body))
            }
        }
    }
}

@Preview(name = "sample-light", widthDp = 393, heightDp = 900)
@Preview(name = "sample-dark", widthDp = 393, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "sample-spanish-large", widthDp = 320, heightDp = 900, locale = "es", fontScale = 1.5f)
@Composable
internal fun ComponentCatalogPreview() { SampleTheme { ComponentCatalog() } }
