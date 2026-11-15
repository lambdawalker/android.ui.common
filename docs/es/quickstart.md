# Inicio rápido

La aplicación conserva el valor del código. Tener todos los dígitos permite intentar verificarlo; no confirma su validez.

```kotlin
package com.apexfission.android.ui.common.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.apexfission.android.ui.common.CodeVerificationCard

/** Compiled documentation example; the host owns verification. */
@Composable
internal fun DocumentationQuickstart() {
    var code by rememberSaveable { mutableStateOf("") }
    CodeVerificationCard(
        value = code,
        onValueChange = { code = it },
        codeLabel = stringResource(R.string.code_label),
        codeContentDescription = stringResource(R.string.code_description),
        pasteLabel = stringResource(R.string.paste),
    )
}
```

Resuelve los textos con los recursos de la aplicación. Usa `enabled = false` o `busyMessage` mientras se procesa una solicitud. `errorMessage` muestra el error; `supportingContent` permite mostrar intentos restantes y ayuda de reenvío. La aplicación debe deshabilitar explícitamente las acciones de `actions` durante la espera.

Las solicitudes de verificación y reenvío, la navegación y los temporizadores pertenecen a la aplicación. `OneTimeCodeField` expone por separado la fila y la acción opcional de pegado. El enfoque automático y el autocompletado requieren activación explícita. Nunca registres el código.
