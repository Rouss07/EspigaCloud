# Visión general del proyecto

## Problema

La Panificadora Espiga de Trigo administraba pedidos, productos e inventario mediante procesos manuales. Esto generaba errores de digitación, pérdida de trazabilidad y demoras en la comunicación entre atención y producción.

## Solución

EspigaCloud es una aplicación web monolítica modular construida con Spring Boot. Centraliza las operaciones de la panificadora y aplica controles de seguridad por roles.

## Objetivos

1. Centralizar productos, tiendas, pedidos y encargos especiales.
2. Reducir errores y mejorar la trazabilidad operativa.
3. Aplicar reglas de validación coherentes en formularios y servidor.
4. Automatizar la calidad mediante un pipeline reproducible.
5. Conservar evidencia técnica y documental para auditoría y mejora continua.

## Módulos

| Módulo | ADMIN | TIENDA | Función |
|---|:---:|:---:|---|
| Usuarios | Sí | No | Cuentas, roles y estado de acceso |
| Productos | Sí | No | Catálogo, precio y stock |
| Tiendas | Sí | No | Sucursales y datos de contacto |
| Pedidos | Sí | Sí | Pedidos diarios por tienda |
| Detalle de pedido | Sí | Sí | Productos y cantidades solicitadas |
| Pedidos especiales | Sí | Sí | Encargos personalizados e imagen |

## Tecnología

- Java 17 y Spring Boot 4.
- Spring MVC, Thymeleaf y Bootstrap.
- Spring Security.
- Spring Data JPA e Hibernate.
- MySQL en contenedores y MariaDB/XAMPP para desarrollo local.
- Maven, Docker y Docker Compose.
- Jenkins, JaCoCo, SonarQube, Playwright y k6.

## Documento rector

El alcance académico y técnico se encuentra en el [Project Charter de EspigaCloud](documentos/Project-Charter-EspigaCloud.docx).
