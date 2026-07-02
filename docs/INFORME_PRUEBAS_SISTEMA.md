# Informe de Pruebas de Sistema y EndToEnd

## Alcance

Validar que EspigaPedidos funcione correctamente en flujos criticos de autenticacion, gestion de productos, tiendas, pedidos, pedidos especiales y navegacion protegida por roles.

## Tipos de pruebas

| Tipo | Herramienta | Evidencia |
|---|---|---|
| Unitarias | JUnit, Mockito | `target/surefire-reports` |
| Integrales | Spring Boot Test, Testcontainers | `target/surefire-reports` |
| E2E Selenium | Selenium + Docker Compose | `target/failsafe-reports` |
| E2E Playwright | Playwright Chromium | `target/playwright` |
| Integrales HTTP | Postman/Newman | `target/postman` |

## Casos de prueba principales

| ID | Caso | Tipo | Resultado esperado |
|---|---|---|---|
| CP-001 | Crear usuario admin inicial | Sistema | Usuario admin creado o mensaje de existencia |
| CP-002 | Login admin | E2E | Acceso al dashboard |
| CP-003 | Producto sin parametros requeridos | Postman/Playwright | Validaciones HTML/servidor activas |
| CP-004 | Crear producto completo | E2E | Producto visible en listado |
| CP-005 | Listar tiendas | Sistema | Vista de tiendas disponible para admin |
| CP-006 | Crear pedido | Integracion/E2E | Pedido guardado y listado |
| CP-007 | Pedido especial con imagen | Unitario/Sistema | Archivo procesado y registro guardado |
| CP-008 | Acceso protegido sin sesion | Sistema | Redireccion a login |

## Criterios de aceptacion

- Todas las pruebas unitarias e integrales pasan.
- Cobertura minima >= 80% en JaCoCo/SonarQube.
- Quality Gate de SonarQube en estado aprobado.
- Pruebas E2E sin fallos criticos.
- Evidencias archivadas por Jenkins.
