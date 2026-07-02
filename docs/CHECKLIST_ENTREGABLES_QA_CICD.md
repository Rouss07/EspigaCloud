# Checklist de Entregables QA, SonarQube y Jenkins

Proyecto: EspigaPedidos

## Estado objetivo

| Item | Entregable | Evidencia en el repositorio | Estado |
|---|---|---|---|
| 1 | Codigo fuente en GitHub/GitLab con pruebas unitarias | `src/test/java`, `pom.xml`, `Jenkinsfile` | Preparado |
| 2 | Codigo fuente con pruebas integrales Postman y validacion de parametros | `postman/EspigaPedidos.postman_collection.json`, etapa `Postman Parametros Entrada` | Preparado |
| 3 | Captura SonarQube cobertura unitarias >= 80% | `target/site/jacoco/index.html`, dashboard SonarQube | Pendiente de captura en Jenkins/Sonar |
| 4 | Informe SonarQube Nivel A, bugs y code smells superados | `sonar-project.properties`, Quality Gate en Jenkins | Pendiente de exportar desde SonarQube |
| 5 | Manual de configuracion CI/CD Back/Front | `docs/MANUAL_CONFIGURACION_CICD.md` | Preparado |
| 6 | Informe Word de pruebas sistema/E2E manuales/automatizadas | `docs/INFORME_PRUEBAS_SISTEMA.md`, `docs/entregables/Informe_Pruebas_Sistema_E2E.docx` | Preparado |
| 7 | Excel de gestion de casos de prueba/defectos | `docs/entregables/Gestion_Casos_Prueba_Defectos.xlsx` | Preparado |
| 8 | Informe de pruebas de seguridad | `docs/INFORME_SEGURIDAD.md`, `target/security` | Preparado |
| 8.1 | Informe pruebas de rendimiento con caracteristicas servidor | `docs/INFORME_RENDIMIENTO.md`, `pruebasK6`, `target/k6` | Preparado |
| 9 | Manual de usuario | `docs/MANUAL_USUARIO.md`, `docs/entregables/Manual_Usuario_EspigaPedidos.docx` | Preparado |
| 10 | Aplicacion funcionando 100% desplegada con URL | `docker-compose.yml`, Jenkins `Ambiente de Pruebas`, parametro `APP_BASE_URL` | Preparado |
| 11 | Ambiente de pruebas funcionando | `docker-compose.yml`, `docker-compose.test.yml` | Preparado |

## Comandos principales

```bash
mvn -B verify -Dspring.profiles.active=test -Dcoverage.minimum=0.80
mvn -B -Pcoverage-100 verify -Dspring.profiles.active=test
docker compose up -d --build
./scripts/ci/run-postman.sh http://localhost:8085
./scripts/ci/run-playwright.sh http://localhost:8085
./scripts/ci/run-zap-baseline.sh http://localhost:8085
./scripts/ci/run-k6-suite.sh http://localhost:8085
```

## Cobertura validada

La compuerta del proyecto acepta `COVERAGE_MINIMUM=1.00` o el perfil Maven `coverage-100`.
La medicion local actual despues de `./mvnw clean verify -Dspring.profiles.active=test -Dcoverage.minimum=1.00` fue:

- Lineas: 100.00%
- Instrucciones: 100.00%
- Metodos: 100.00%
- Clases: 100.00%
- Ramas: 93.75%

El requisito de cobertura >=80% queda superado y el modo estricto de 100% para lineas/instrucciones tambien queda operativo.
