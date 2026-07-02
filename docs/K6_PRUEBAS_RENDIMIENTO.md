# Pruebas de rendimiento con K6

Se agregan evidencias y documentación para las pruebas de rendimiento del sistema.

## Funcionalidad trabajada

Se plantea la validación de carga para los endpoints principales del sistema, considerando operaciones de consulta, registro, actualización y eliminación lógica.

## Objetivo

Verificar que los controladores respondan correctamente bajo carga y que los tiempos de respuesta sean aceptables.

## Escenarios considerados

- Pruebas de carga para pedidos.
- Pruebas de carga para productos.
- Pruebas de carga para usuarios.
- Validación de tasa de errores.
- Medición de tiempo de respuesta.

## Resultado esperado

Los endpoints deben mantener una tasa de error baja y responder correctamente durante la ejecución de usuarios virtuales.

## Ejecución

Antes de correr K6, levanta la aplicación y confirma que existe el usuario `admin` con la contraseña que usarás en las pruebas.

Variables soportadas por los scripts:

- `BASE_URL`: URL base de la aplicación. Por defecto `http://localhost:8080`.
- `ADMIN_PASSWORD`: contraseña del usuario `admin`. Por defecto `1234`.
- `PEDIDO_ID`: pedido existente para pruebas de detalle de pedido. Por defecto `1`.
- `PRODUCTO_ID`: producto existente para pruebas de detalle de pedido. Por defecto `1`.
- `TIENDA_ID`: tienda existente para pedidos especiales. Por defecto `1`.

Ejemplos:

```bash
k6 run -e ADMIN_PASSWORD=1234 pruebasK6/Login/smoke-test.js
k6 run -e ADMIN_PASSWORD=1234 pruebasK6/Producto/load-test.js
k6 run -e ADMIN_PASSWORD=1234 -e PEDIDO_ID=1 -e PRODUCTO_ID=1 pruebasK6/DetallePedido/load-test.js
k6 run -e ADMIN_PASSWORD=1234 -e TIENDA_ID=1 pruebasK6/PedidoEspecial/load-test.js
```
