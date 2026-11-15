# Migración

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

Card Detector y Permissions migran únicamente sus ejemplos. Auth delega la presentación, conservando controladores y navegación. Las migraciones siguen como borradores hasta confirmar el primer artefacto de UI Common; antes se validan mediante sustitución explícita de código fuente.
