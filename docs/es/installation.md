# Instalación

Requiere Android API 28+, bytecode Java 17 y una versión compatible de Kotlin/Compose. Las versiones de herramientas coinciden con Demo Common. La aplicación de ejemplo no se publica como biblioteca.

Consulta [IMPORT.md](../../IMPORT.md) para obtener coordenadas confirmadas. Todavía no existen. Para validar la migración, descarga un commit exacto de UI Common y configura una compilación compuesta en el consumidor:

```kotlin
providers.gradleProperty("uiCommonCheckout").orNull?.let { checkout ->
    includeBuild(checkout) {
        dependencySubstitution {
            substitute(module("com.apexfission.android:ui-common"))
                .using(project(":ui-common"))
        }
    }
}
```

La dependencia `com.apexfission.android:ui-common:0.0.0-SNAPSHOT` es únicamente una clave de sustitución de código fuente, no una versión publicada. Ejecuta Gradle con `-PuiCommonCheckout=/ruta/absoluta/al/checkout`. Las migraciones permanecen bloqueadas hasta sustituirla por una publicación confirmada. No uses mavenLocal sin limitar su alcance.

Después de publicar, utiliza las coordenadas exactas de IMPORT.md. Card Detector y Permissions dependen de UI Common solo en sus aplicaciones de ejemplo; la biblioteca Permissions conserva API 24 y no depende de UI Common.
