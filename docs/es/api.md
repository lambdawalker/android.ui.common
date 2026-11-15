# Contratos de API

Todos los componentes pertenecen a `com.apexfission.android.ui.common`, aceptan Modifier y heredan MaterialTheme. No instalan un tema propio ni conocen servicios de autenticación.

| Componente | Responsabilidad |
| --- | --- |
| ContentCard | Tarjeta elevada con contenido ColumnScope, relleno y separación |
| ActionCard | Título, descripción, etiqueta y callback de acción, icono opcional y estado habilitado |
| SectionHeading | Encabezado accesible e icono decorativo opcional |
| EmptyStateCard | Título, descripción y acciones proporcionados por la aplicación |
| CatalogScreen | Título, descripción, texto superior, ancho, relleno, insets, acciones y contenido diferido |
| OneTimeCodeField | Valor y callback, longitud positiva (6 por defecto), enfoque, accesibilidad, pegado y autocompletado |
| CodeVerificationCard | Campo, instrucciones, destinatario, mensajes de espera/error, contenido de apoyo y acciones |

CatalogScreen consume los insets del Scaffold una vez y agrega el relleno solicitado. Oculta la descripción cuando `showEmptyState` es true; la aplicación proporciona `emptyState`. El contenido LazyListScope sigue disponible y las claves del encabezado están reservadas.

OneTimeCodeField es un único campo editable. Las celdas decorativas no duplican la lectura accesible. Al escribir o pegar filtra con Char.isDigit y trunca a codeLength; conserva los dígitos Unicode admitidos por Auth. El valor externo debe ser válido: no se modifica silenciosamente durante la composición. El servidor valida el código. Longitudes muy grandes requieren adaptar el diseño.

Solo se lee el portapapeles al pulsar Pegar. El autocompletado es opcional y la aplicación elige ContentType. Su comportamiento real necesita pruebas en dispositivo. Completar el código no envía ninguna solicitud automáticamente.

CodeVerificationCard deshabilita el campo y Pegar cuando busyMessage no es nulo. Las acciones composables siguen bajo control de la aplicación y deben deshabilitarse allí. Los mensajes son presentación; el módulo no guarda credenciales ni accede a la red.
