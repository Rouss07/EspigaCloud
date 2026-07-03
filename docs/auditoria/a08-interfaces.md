# A08 - Interfaces y servicios

## Alcance real

La versión actual expone principalmente una aplicación MVC con formularios HTML procesados mediante `@ModelAttribute`. Los módulos no constituyen una API REST JSON pública completa.

## Interfaces revisadas

- rutas web para usuarios, productos, tiendas y pedidos;
- formularios protegidos con CSRF;
- colección Postman para disponibilidad, login y parámetros;
- integración de pruebas mediante HTTP.

## Resultado

**Cumple parcialmente.** Las interfaces web están operativas y probadas. Si se requiere integración externa mediante JSON, debe diseñarse una API `/api/**` con DTO, `@RequestBody`, manejo uniforme de errores y documentación OpenAPI.

## Acción recomendada

No declarar una API REST como finalizada hasta disponer de contratos JSON, códigos HTTP, versionado, autenticación y pruebas específicas.
