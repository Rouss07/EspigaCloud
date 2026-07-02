# Informe de Pruebas de Seguridad

## Alcance

Evaluacion dinamica basica del ambiente de pruebas de EspigaPedidos mediante OWASP ZAP Baseline, complementada por revision de Quality Gate de SonarQube.

## Herramientas

| Herramienta | Proposito |
|---|---|
| OWASP ZAP | DAST baseline sobre `APP_BASE_URL` |
| SonarQube | Analisis estatico de bugs, vulnerabilidades, hotspots y code smells |
| Jenkins | Orquestacion y archivado de evidencias |

## Ejecucion

```bash
docker compose up -d --build
./scripts/ci/run-zap-baseline.sh http://localhost:8085
```

## Evidencias

| Evidencia | Ruta |
|---|---|
| Reporte HTML ZAP | `target/security/zap-report.html` |
| Reporte JSON ZAP | `target/security/zap-report.json` |
| Reporte XML ZAP | `target/security/zap-report.xml` |
| SonarQube | Dashboard del proyecto `com.espigapedidos:espigapedidos` |

## Criterios de aprobacion

- Sin alertas ZAP de riesgo alto.
- Riesgos medios justificados o corregidos.
- Quality Gate de SonarQube aprobado.
- Bugs, vulnerabilidades y code smells criticos superados.
