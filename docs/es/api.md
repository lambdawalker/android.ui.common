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
| VerificationCardDetails | Textos de la aplicación para longitud del código, explicación y estado del destinatario |
| VerificationRecipientRow | Destinatario y estado opcional con ajuste de línea e iconos decorativos al inicio/final |

CatalogScreen consume los insets del Scaffold una vez y agrega el relleno solicitado. Oculta la descripción cuando `showEmptyState` es true; la aplicación proporciona `emptyState`. El contenido LazyListScope sigue disponible y las claves del encabezado están reservadas.

OneTimeCodeField es un único campo editable. Las celdas decorativas no duplican la lectura accesible. Al escribir o pegar filtra con Char.isDigit y trunca a codeLength; conserva los dígitos Unicode admitidos por Auth. El valor externo debe ser válido: no se modifica silenciosamente durante la composición. El servidor valida el código. Longitudes muy grandes requieren adaptar el diseño.

Solo se lee el portapapeles al pulsar Pegar. El autocompletado es opcional y la aplicación elige ContentType. Su comportamiento real necesita pruebas en dispositivo. Completar el código no envía ninguna solicitud automáticamente.

CodeVerificationCard deshabilita el campo y Pegar cuando busyMessage no es nulo. Las acciones composables siguen bajo control de la aplicación y deben deshabilitarse allí. Los mensajes son presentación; el módulo no guarda credenciales ni accede a la red.

En Development, las instrucciones aparecen sobre las celdas redondeadas, la etiqueta y longitud
debajo, y después la explicación y el resumen del destinatario. `VerificationCardDetails` selecciona
la sobrecarga detallada; la firma original y su lambda final de acciones siguen disponibles. La nueva
sobrecarga y la fila del destinatario no existen en el artefacto confirmado 0.1.0. Usa integración
desde el código fuente hasta confirmar otra versión. Ambas sobrecargas heredan formas, colores y
tipografía del host; no instalan una paleta de marca. La celda enfocada muestra un contorno con el
color primario del host y crece con el tamaño de texto.

El estado del destinatario no cambia por introducir todos los dígitos. Proporciona estado real del
host; no deduzcas éxito o apertura del enlace a partir del código. Los iconos decorativos deben tener
una descripción nula porque el texto contiguo comunica su significado. `pasteLabel` nulo oculta el
botón auxiliar sin leer el portapapeles; la edición nativa del campo permanece disponible.
