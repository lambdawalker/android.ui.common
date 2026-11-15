# Source reference

Generated excerpts from reviewed source. Read [API contracts](api.md) for state ownership and supported behavior. Internal normalization and private helpers are not public APIs.

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/ActionCard.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/ActionCard.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
public fun ActionCard(
    title: String,
    description: String,
    actionLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/CatalogScreen.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/CatalogScreen.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
public fun CatalogScreen(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    maxContentWidth: Dp = 640.dp,
    contentPadding: PaddingValues = PaddingValues(24.dp),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    itemSpacing: Dp = 16.dp,
    showEmptyState: Boolean = false,
    headerActions: (@Composable () -> Unit)? = null,
    emptyState: (@Composable () -> Unit)? = null,
    content: LazyListScope.() -> Unit = {},
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/CodeVerificationCard.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/CodeVerificationCard.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
public fun CodeVerificationCard(
    value: String,
    onValueChange: (String) -> Unit,
    codeLabel: String,
    codeContentDescription: String,
    pasteLabel: String?,
    modifier: Modifier = Modifier,
    instructions: String? = null,
    recipient: String? = null,
    codeLength: Int = 6,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    autofillContentType: ContentType? = null,
    busyMessage: String? = null,
    errorMessage: String? = null,
    supportingContent: (@Composable ColumnScope.() -> Unit)? = null,
    actions: (@Composable ColumnScope.() -> Unit)? = null,
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/ContentCard.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/ContentCard.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
public fun ContentCard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    itemSpacing: Dp = 12.dp,
    content: @Composable ColumnScope.() -> Unit,
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/EmptyStateCard.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/EmptyStateCard.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
public fun EmptyStateCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actions: (@Composable ColumnScope.() -> Unit)? = null,
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/OneTimeCodeField.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/OneTimeCodeField.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Suppress("DEPRECATION")
@Composable
public fun OneTimeCodeField(
    value: String,
    onValueChange: (String) -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    codeLength: Int = 6,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    pasteLabel: String? = null,
    autofillContentType: ContentType? = null,
)
```

## `ui-common/src/main/kotlin/com/apexfission/android/ui/common/SectionHeading.kt`

[Source](../../ui-common/src/main/kotlin/com/apexfission/android/ui/common/SectionHeading.kt)

```kotlin
package com.apexfission.android.ui.common
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
public fun SectionHeading(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
)
```
