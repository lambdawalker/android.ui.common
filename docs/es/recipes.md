# Recetas

Combina ContentCard y SectionHeading para contenido personalizado, o usa ActionCard para una acción principal. `CatalogScreen.headerActions` permite agregar Ajustes: la aplicación proporciona textos localizados, iconos y navegación. Configura explícitamente `showEmptyState` y `emptyState`.

Coloca los componentes dentro del MaterialTheme de la aplicación. Los colores, la tipografía y las formas provienen de ese tema. Personaliza Modifier, el relleno y la separación de ContentCard, y el ancho e insets de CatalogScreen. No uses el tema del ejemplo dentro de la biblioteca.

En Auth, conserva Verificar y Reenviar en el marco existente. CodeVerificationCard contiene la tarjeta; sus acciones permiten cambiar la dirección o iniciar sesión. El controlador conserva los enlaces de correo, los plazos de reenvío, los límites de intentos y el estado de espera/error. La extracción no modifica el protocolo.

## Lista de demostraciones del ejemplo

Instala el módulo no publicable `:app` con `./gradlew :app:installDebug` y abre **UI Common**.
La pantalla inicial muestra una lista desplazable con **Ajustes de la aplicación** encima de las
entradas. Toda la fila es pulsable y abre un ejemplo independiente; **Volver a las demostraciones**
y Atrás del sistema regresan a la posición anterior de la lista. Cada ejemplo también ofrece Ajustes.

| Demostración | Componentes e interacción |
| --- | --- |
| Verificación de código | `CodeVerificationCard`; escribir/pegar, simular un error con Verificar, recorrer Listo/Error/Ocupado/Deshabilitado o Restablecer |
| Entrada de código | `OneTimeCodeField`; pegado explícito, selección, edición y Restablecer |
| Tarjeta de acción | `ActionCard`; alternar la etiqueta mediante su callback |
| Contenido y encabezados | `ContentCard` con `SectionHeading` y texto de ejemplo |
| Resumen del destinatario | `VerificationRecipientRow` con dirección sintética y estado proporcionado por el host |
| Estado vacío | `CatalogScreen.showEmptyState` y `EmptyStateCard` |

`CatalogScreen` proporciona el diseño de la lista y los ejemplos. La navegación y el estado
pertenecen solo a la aplicación de ejemplo. Girar el dispositivo conserva el ejemplo seleccionado
y su estado; salir y volver a abrirlo empieza de nuevo. La verificación es local y no envía correo.
Los textos están disponibles en inglés y español. No hace falta publicar la biblioteca para ejecutar
este cambio de la aplicación de ejemplo.
