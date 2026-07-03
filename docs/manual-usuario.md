# Manual de usuario

## Requisitos

- Navegador actualizado: Chrome, Edge o Firefox.
- Acceso al servidor donde se ejecuta EspigaCloud.
- Usuario y contraseña entregados por el administrador.

## Inicio de sesión

1. Abra la dirección de EspigaCloud; localmente suele ser `http://localhost:8080`.
2. Escriba el usuario y la contraseña.
3. Presione **Ingresar**.
4. Si las credenciales son incorrectas, revise el mensaje del formulario.

## Panel principal

Después de autenticarse se muestran accesos a productos, tiendas, pedidos y pedidos especiales. Las opciones dependen del rol conectado.

## Usuarios

Disponible para `ADMIN`.

1. Ingrese a **Usuarios**.
2. Use **Nuevo usuario** para crear una cuenta.
3. Complete nombre, username, contraseña, rol y estado.
4. Guarde o edite registros existentes.

La contraseña debe tener al menos ocho caracteres y el username admite letras, números, punto, guion y guion bajo.

## Productos

Disponible para `ADMIN`.

1. Ingrese a **Productos** y seleccione **Nuevo producto**.
2. Complete nombre y categoría.
3. Ingrese un precio mayor que `0`, con máximo dos decimales.
4. Ingrese stock entero desde `1`.
5. Guarde el producto.

## Tiendas

Disponible para `ADMIN`. Registre nombre, dirección, teléfono de 7 a 15 dígitos y estado `Activo` o `Inactivo`.

## Pedidos

Disponible para `ADMIN` y `TIENDA`.

1. Cree un pedido indicando fecha, estado y tienda.
2. Abra su detalle.
3. Seleccione productos y cantidades enteras mayores que `0`.

## Pedidos especiales

Registre cliente, teléfono, descripción, sabor, tamaño, fecha futura o actual, estado, tienda e imagen opcional. El sistema rechaza fechas pasadas y teléfonos con letras.

## Cerrar sesión

Use **Cerrar sesión** en la barra superior para finalizar de forma segura.

## Manual ilustrado

Descargue el documento original con capturas y descripción completa de los módulos:

[Descargar Manual de Usuario EspigaCloud (PDF)](documentos/Manual-Usuario-EspigaCloud.pdf){ .md-button .md-button--primary }
