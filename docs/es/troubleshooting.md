# Solución de problemas y límites

- Si no se resuelve la dependencia, verifica JitPack y las coordenadas exactas de IMPORT.md. Elimina el snapshot anterior y la sustitución de código fuente al usar la versión confirmada.
- enabled=false o busyMessage no nulo deshabilitan el campo. La aplicación controla cuándo termina la espera.
- Pegar filtra dígitos y trunca a codeLength. Solo lee el portapapeles al pulsar la acción.
- Deshabilita explícitamente las acciones de la aplicación durante una operación.
- Las diferencias visuales provienen del MaterialTheme activo; la biblioteca no impone una marca.
- Respeta las versiones de SDK/JDK/Compose del proyecto. No las reduzcas para eludir fallos locales.
- Revisa código y guías antes de regenerar un manifiesto de fuentes desactualizado.
- Autocompletado, selección y teclado requieren pruebas en emulador o dispositivo; las capturas no bastan.
