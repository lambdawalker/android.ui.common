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
internal fun DemoPreviewHost(alternate: Boolean = false, content: @Composable () -> Unit) {
    val scheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    val typography = Typography()
    MaterialTheme(typography = if (alternate) typography.copy(bodyLarge = typography.bodyLarge.copy(fontFamily = FontFamily.Serif)) else typography,
        colorScheme = if (alternate) scheme.copy(primary = Color(0xFF806000)) else scheme,
        shapes = if (alternate) Shapes(small = RoundedCornerShape(0.dp)) else Shapes()) {
        Surface { content() }
    }
}

@Preview(name = "components-light", widthDp = 393)
@Preview(name = "components-dark", widthDp = 393, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun ComponentsPreview() { DemoPreviewHost {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ContentCard { SectionHeading("Section heading"); Text("Custom content") }
        ActionCard("Action card", "Host-owned action", "Continue", {})
        EmptyStateCard("No content", "Add your first item")
    }
} }

@Preview(name = "catalog-light", widthDp = 393, heightDp = 700)
@Preview(name = "catalog-dark", widthDp = 393, heightDp = 700, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun CatalogPreview() { DemoPreviewHost {
    CatalogScreen("Catalog", "Choose an item") {
        item { ActionCard("Item", "Description", "Open", {}) }
    }
} }
