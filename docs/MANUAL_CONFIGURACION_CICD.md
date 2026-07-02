# Manual de Configuracion de Herramientas y Pipeline CI/CD

## Herramientas

| Herramienta | Uso |
|---|---|
| GitHub/GitLab | Repositorio fuente y disparador del pipeline |
| Jenkins | Orquestacion CI/CD |
| Maven | Build, pruebas unitarias, integrales y JaCoCo |
| JaCoCo | Cobertura de pruebas |
| SonarQube | Calidad, bugs, vulnerabilidades, code smells y Quality Gate |
| Docker / Docker Compose | Empaquetado y ambiente de pruebas |
| Postman/Newman | Pruebas integrales y validacion de parametros |
| Playwright | Pruebas EndToEnd automatizadas |
| OWASP ZAP | Pruebas de seguridad dinamicas |
| k6 | Pruebas de rendimiento |

## Credenciales Jenkins requeridas

| ID | Descripcion |
|---|---|
| `admin-password` | Password del usuario admin creado por `/setup-admin` |
| `tienda-password` | Password del usuario tienda creado por `/setup-tienda` |

## Configuracion SonarQube

1. Crear un proyecto con key `com.espigapedidos:espigapedidos`.
2. Configurar en Jenkins una instalacion llamada `SonarQube`.
3. Crear un Quality Gate Nivel A con estas condiciones minimas:
   - Bugs: 0
   - Vulnerabilities: 0
   - Security Hotspots revisados: 100%
   - Code Smells nuevos: 0 o mantenibilidad A
   - Coverage: >= 80%
   - Duplicated Lines: bajo el limite definido por la institucion
4. El archivo `sonar-project.properties` y el `pom.xml` publican `target/site/jacoco/jacoco.xml`.

## Pipeline Jenkins

El `Jenkinsfile` ejecuta estas etapas:

1. Checkout desde GitHub/GitLab.
2. Build Maven.
3. Pruebas unitarias e integrales con JaCoCo.
4. Analisis SonarQube con espera de Quality Gate.
5. Build Docker.
6. Ambiente de pruebas con Docker Compose.
7. E2E Selenium.
8. Postman/Newman.
9. Playwright.
10. OWASP ZAP.
11. k6.

## Parametros importantes

| Parametro | Valor recomendado | Uso |
|---|---:|---|
| `COVERAGE_MINIMUM` | `0.80` | Requisito minimo >=80% |
| `COVERAGE_MINIMUM` | `1.00` | Exigencia 100% cuando el proyecto este totalmente cubierto |
| `DEPLOY_PORT` | `8085` | Puerto del ambiente de pruebas |
| `APP_BASE_URL` | `http://localhost:8085` | URL usada por Postman, Playwright, ZAP y k6 |

## Evidencias generadas

| Evidencia | Ruta |
|---|---|
| JaCoCo HTML/XML | `target/site/jacoco` |
| Reportes JUnit | `target/surefire-reports`, `target/failsafe-reports` |
| Postman | `target/postman` |
| Playwright | `target/playwright` |
| OWASP ZAP | `target/security` |
| k6 | `target/k6` |
| Entregables Word/Excel | `docs/entregables` |
