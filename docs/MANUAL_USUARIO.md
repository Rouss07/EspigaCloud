# Manual de Usuario - EspigaPedidos

## Acceso

1. Abrir la URL del ambiente de pruebas, por ejemplo `http://localhost:8085`.
2. Iniciar sesion con el usuario asignado.
3. Si es la primera ejecucion, crear usuarios base:
   - `/setup-admin`
   - `/setup-tienda`

## Roles

| Rol | Permisos principales |
|---|---|
| ADMIN | Gestion de usuarios, productos, tiendas, pedidos y pedidos especiales |
| TIENDA | Gestion de pedidos y pedidos especiales permitidos |

## Productos

1. Ir a `Productos`.
2. Seleccionar `Nuevo`.
3. Completar nombre, categoria, precio y stock.
4. Guardar.
5. Desde el listado se puede editar o eliminar.

## Tiendas

1. Ir a `Tiendas`.
2. Registrar nombre, direccion, telefono y estado.
3. Guardar.
4. Usar editar o eliminar desde el listado cuando corresponda.

## Pedidos

1. Ir a `Pedidos`.
2. Crear un pedido seleccionando la tienda.
3. Guardar.
4. Acceder al detalle para agregar productos y cantidades.

## Pedidos especiales

1. Ir a `Pedidos especiales`.
2. Completar cliente, telefono, descripcion, sabor, tamano, fecha de entrega y tienda.
3. Adjuntar imagen si aplica.
4. Guardar y revisar el listado.

## Cierre de sesion

Usar la opcion de salir/cerrar sesion en la barra de navegacion.
