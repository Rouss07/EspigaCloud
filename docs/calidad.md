# Pruebas, cobertura y SonarQube

## Estrategia de calidad

La calidad se verifica en varias capas:

| Nivel | Herramienta | Cobertura funcional |
|---|---|---|
| Unitaria | JUnit y Mockito | Servicios, controladores, entidades y configuración |
| Integración | Spring Boot Test y H2 | Repositorios y flujos entre capas |
| E2E | Playwright | Login, navegación, validaciones y creación de productos |
| Rendimiento | k6 | Login, productos, tiendas, pedidos especiales y detalles |
| Cobertura | JaCoCo | Líneas e instrucciones Java |
| Calidad estática | SonarQube | Bugs, vulnerabilidades, deuda y duplicación |

## Resultados base verificados

- **112 pruebas Java**, sin fallos.
- **5 pruebas Playwright**, sin fallos en la última verificación local.
- JaCoCo observado: aproximadamente **93% de líneas** y **91% de instrucciones** después de incorporar validaciones.
- Umbral mínimo del pipeline: **80%**.

!!! warning "Métrica viva"
    Los porcentajes cambian con el código. Consulte el reporte JaCoCo archivado por Jenkins y el panel SonarQube para el valor de la ejecución más reciente.

## Ejecutar pruebas

```powershell
.\mvnw.cmd clean verify "-Dspring.profiles.active=test" "-Dcoverage.minimum=0.80"
```

El reporte de cobertura se genera en:

```text
target/site/jacoco/index.html
```

## SonarQube

En el entorno local:

```text
http://localhost:9000
```

Jenkins envía el archivo XML de JaCoCo a SonarQube y espera el **Quality Gate**. Si una condición falla, el pipeline se detiene para impedir que un resultado no conforme continúe como exitoso.

Las condiciones recomendadas incluyen:

- cobertura igual o superior al 80%;
- cero vulnerabilidades nuevas;
- cero bugs nuevos;
- revisión de hotspots de seguridad;
- duplicación y mantenibilidad dentro del límite acordado.

## Evidencias

- [Informe SonarQube](INFORME_SONARQUBE.md)
- [Informe de pruebas del sistema](INFORME_PRUEBAS_SISTEMA.md)
- [Informe de seguridad](INFORME_SEGURIDAD.md)
- [Informe de rendimiento](INFORME_RENDIMIENTO.md)
