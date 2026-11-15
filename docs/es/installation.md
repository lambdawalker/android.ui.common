# Instalación

UI Common requiere Android API 28+, bytecode Java 17 y una versión compatible de Kotlin/Compose.
La aplicación de ejemplo no se publica como biblioteca.

La versión **0.1.0** está confirmada en JitPack. Agrega JitPack a los repositorios de dependencias en
settings.gradle.kts y después agrega la dependencia al módulo consumidor:

```kotlin
maven { url = uri("https://jitpack.io") }
```

```kotlin
implementation("com.github.lambdawalker:android.ui.common:ui-common~v0.1.0-jitpack")
```

[IMPORT.md](../../IMPORT.md) se genera a partir de publicaciones confirmadas e incluye ejemplos de
Kotlin DSL, Groovy, catálogo de versiones y Maven. Esta versión usa coordenadas de JitPack; configurar
`com.apexfission.android:ui-common` no demuestra que exista una publicación en Maven Central.

Las compilaciones normales no necesitan un checkout de UI Common ni sustitución de dependencias.
Elimina la clave temporal `0.0.0-SNAPSHOT` y el bloque `uiCommonCheckout` al migrar a esta versión.
Para desarrollar desde el código fuente, utiliza una compilación compuesta aislada que sustituya
explícitamente la identidad del módulo publicado. No agregues mavenLocal sin limitar su alcance.

Card Detector y Permissions dependen de UI Common solo en sus aplicaciones de ejemplo. La biblioteca
Permissions conserva API 24 y no depende de UI Common. Auth utiliza las tarjetas en su biblioteca.
