# Instalación

## Separación de módulos en desarrollo

El código actual contiene dos bibliotecas independientes y una aplicación de catálogo no publicada:

| Módulo | Componentes |
| --- | --- |
| `:ui-demo-common` | ContentCard, ActionCard, SectionHeading, EmptyStateCard, CatalogScreen |
| `:ui-code-verification` | OneTimeCodeField, CodeVerificationCard (ambas sobrecargas), VerificationCardDetails, VerificationRecipientRow |
| `:app` | Catálogo unificado, navegación, ejemplos y controles de vista previa; depende de ambas bibliotecas |

Ninguna biblioteca depende de la otra. Los paquetes Kotlin conservan `com.apexfission.android.ui.common`;
los espacios de nombres de recursos Android son `com.apexfission.android.ui.demo` y
`com.apexfission.android.ui.codeverification`. Se conservan API 28, Java 17, el tema del host y el estado controlado por el consumidor.

Los módulos nuevos están en **Desarrollo/integración desde código fuente**, sin versiones publicadas confirmadas.
En una compilación local multiproyecto, usa `project(":ui-demo-common")` y/o
`project(":ui-code-verification")`. Sustituye la dependencia monolítica en todo el grafo de dependencias:
no combines el AAR antiguo con ninguna biblioteca extraída, porque sus clases Kotlin se duplicarían.
La migración de consumidores externos y la automatización de publicación quedan pendientes de la siguiente etapa.
Los registros históricos no cambian; la información de versiones que aparece abajo corresponde al artefacto monolítico anterior.


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
