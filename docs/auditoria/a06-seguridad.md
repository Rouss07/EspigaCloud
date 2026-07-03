# A06 - Seguridad

## Controles evaluados

- autenticación con Spring Security;
- autorización por roles `ADMIN` y `TIENDA`;
- protección CSRF;
- cifrado de contraseñas;
- validación de entradas y archivos;
- gestión de secretos mediante variables de entorno.

## Resultado

**Cumple parcialmente.** La aplicación dispone de controles sólidos para su alcance académico. La auditoría señaló como riesgo alto el uso histórico de contraseñas iniciales fijas; la configuración actual permite externalizarlas.

## Recomendaciones

- usar secretos administrados en Jenkins y producción;
- rotar credenciales;
- ejecutar análisis DAST y dependencias;
- limitar tamaño y tipo real de archivos cargados;
- registrar eventos de autenticación sin exponer datos sensibles.

[Consultar informe de seguridad](../INFORME_SEGURIDAD.md)
