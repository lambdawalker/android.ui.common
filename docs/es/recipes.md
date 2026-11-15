# Recetas

Combina ContentCard y SectionHeading para contenido personalizado, o usa ActionCard para una acción principal. `CatalogScreen.headerActions` permite agregar Ajustes: la aplicación proporciona textos localizados, iconos y navegación. Configura explícitamente `showEmptyState` y `emptyState`.

Coloca los componentes dentro del MaterialTheme de la aplicación. Los colores, la tipografía y las formas provienen de ese tema. Personaliza Modifier, el relleno y la separación de ContentCard, y el ancho e insets de CatalogScreen. No uses el tema del ejemplo dentro de la biblioteca.

En Auth, conserva Verificar y Reenviar en el marco existente. CodeVerificationCard contiene la tarjeta; sus acciones permiten cambiar la dirección o iniciar sesión. El controlador conserva los enlaces de correo, los plazos de reenvío, los límites de intentos y el estado de espera/error. La extracción no modifica el protocolo.
