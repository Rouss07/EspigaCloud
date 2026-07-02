# Informe SonarQube - Nivel A

## Proyecto

| Campo | Valor |
|---|---|
| Nombre | EspigaPedidos |
| Project Key | `com.espigapedidos:espigapedidos` |
| Rama | Principal del repositorio GitHub/GitLab |
| Fuente cobertura | `target/site/jacoco/jacoco.xml` |

## Resultado esperado del Quality Gate

| Metrica | Criterio |
|---|---|
| Bugs | 0 |
| Vulnerabilidades | 0 |
| Security Hotspots | Revisados o justificados |
| Code Smells criticos | 0 |
| Maintainability Rating | A |
| Reliability Rating | A |
| Security Rating | A |
| Coverage | >= 80%, con modo estricto 100% para lineas/instrucciones |

## Cobertura local validada

| Contador JaCoCo | Resultado |
|---|---:|
| Lineas | 100.00% |
| Instrucciones | 100.00% |
| Metodos | 100.00% |
| Clases | 100.00% |
| Ramas | 93.75% |

## Evidencias

| Evidencia | Ubicacion |
|---|---|
| Reporte JaCoCo HTML | `target/site/jacoco/index.html` |
| Reporte JaCoCo XML | `target/site/jacoco/jacoco.xml` |
| Captura SonarQube cobertura | Pendiente de tomar desde dashboard SonarQube |
| Informe bugs/code smells | Exportar desde SonarQube despues del analisis Jenkins |

## Resultado

El proyecto queda configurado para enviar cobertura y resultados de pruebas a SonarQube desde Jenkins. La compuerta de cobertura 100% para lineas e instrucciones fue validada localmente con:

```bash
./mvnw clean verify -Dspring.profiles.active=test -Dcoverage.minimum=1.00
```
