# Informe de Pruebas de Rendimiento

## Alcance

Validar disponibilidad y tiempos de respuesta iniciales de los flujos principales con k6.

## Caracteristicas del servidor de referencia

| Componente | Valor |
|---|---|
| Aplicacion | Spring Boot 4 / Java 17 |
| Base de datos | MySQL 8.4 |
| Contenedores | Docker Compose |
| Puerto ambiente | `8085` por defecto |
| URL base | `APP_BASE_URL` |

## Ejecucion

```bash
docker compose up -d --build
./scripts/ci/run-k6-suite.sh http://localhost:8085
```

## Escenarios disponibles

| Modulo | Scripts |
|---|---|
| Login | `pruebasK6/Login/smoke-test.js`, `load-test.js`, `stress-test.js` |
| Producto | `pruebasK6/Producto/smoke-test.js`, `load-test.js`, `stress-test.js` |
| Tienda | `pruebasK6/Tienda/smoke-test.js`, `load-test.js`, `stress-test.js` |
| DetallePedido | `pruebasK6/DetallePedido/smoke-test.js`, `load-test.js`, `stress-test.js` |
| PedidoEspecial | `pruebasK6/PedidoEspecial/smoke-test.js`, `load-test.js`, `stress-test.js` |

## Evidencias

Los summaries JSON se guardan en `target/k6`.

## Criterios sugeridos

- Sin errores HTTP 5xx.
- Tasa de fallos menor a 1%.
- Percentil 95 de respuestas bajo el umbral acordado para el servidor de pruebas.
