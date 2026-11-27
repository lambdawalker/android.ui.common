# Migración

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


| API anterior | Reemplazo |
| --- | --- |
| DemoCard(content) | ContentCard(content) |
| DemoCard(title, description, actionLabel, onClick) | ActionCard |
| CardTitle | SectionHeading |
| DemoScreen | CatalogScreen |
| DemoCatalogEmptyState (privado) | EmptyStateCard |
| EmailOneTimeCodeField (interno de Auth) | OneTimeCodeField |
| Tarjeta interna de EmailWaitScreen | CodeVerificationCard |

Los imports pasan a `com.apexfission.android.ui.common`. CatalogScreen renombra welcomeNote a description y reemplaza Ajustes por headerActions. Los textos del estado vacío pertenecen a la aplicación. Actualiza también recursos y aliases de dependencias en las pruebas.

No existen wrappers de compatibilidad. Las versiones publicadas de Demo Common permanecen intactas. Origen: Demo Common `00375200862e82da838ee75545c779505cdc8ca0`; Auth comienza en `f356a5b`, con la licencia Apache 2.0 integrada y el flujo de onboarding más reciente. Se conserva la atribución.

Card Detector y Permissions migran únicamente sus ejemplos. Auth delega la presentación, conservando controladores y navegación. Los consumidores ya pueden utilizar la versión 0.1.0 confirmada en JitPack. Reemplaza la sustitución temporal por la dependencia publicada de [Instalación](installation.md) y valida la compilación.
