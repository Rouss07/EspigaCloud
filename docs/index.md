<div class="hero">
  <h1>EspigaCloud</h1>
  <p>Sistema web para la gestión integral de pedidos de la Panificadora Espiga de Trigo.</p>
  <p>Documentación funcional, técnica, de calidad, CI/CD y auditoría SDLC.</p>
</div>

## Centro documental

Este sitio reúne en un único lugar la documentación del producto desarrollado por **DulceCode**. Incluye el manual de usuario, la arquitectura, las pruebas automatizadas, la cobertura, SonarQube, Jenkins y el expediente completo de auditoría.

<div class="metric-grid">
  <div class="metric"><strong>112</strong>pruebas Java verificadas</div>
  <div class="metric"><strong>5</strong>pruebas Playwright E2E</div>
  <div class="metric"><strong>15</strong>documentos fuente</div>
  <div class="metric"><strong>57%</strong>cumplimiento SDLC auditado</div>
</div>

## Accesos rápidos

- [Comenzar con el manual de usuario](manual-usuario.md)
- [Consultar la auditoría SDLC](auditoria/index.md)
- [Revisar pruebas, cobertura y SonarQube](calidad.md)
- [Entender el pipeline Jenkins](cicd.md)
- [Descargar todas las evidencias](evidencias.md)
- [Abrir el código fuente en GitHub](https://github.com/Rouss07/EspigaCloud)

!!! info "Estado de la evidencia"
    Los documentos de auditoría corresponden a una autoevaluación académica formal del ciclo de vida del software. Las métricas técnicas pueden evolucionar con cada ejecución del pipeline; Jenkins y SonarQube son la fuente operativa más reciente.

## Alcance del sistema

EspigaCloud centraliza:

- usuarios y roles `ADMIN` / `TIENDA`;
- productos, categorías, precios y stock;
- tiendas o puntos de venta;
- pedidos diarios y sus detalles;
- pedidos especiales con datos del cliente e imagen de referencia;
- autenticación y autorización mediante Spring Security;
- calidad continua mediante JUnit, JaCoCo, Playwright, k6, Jenkins y SonarQube.

## Equipo

| Rol | Integrante |
|---|---|
| Auditoría líder, backend, seguridad y calidad | Rosa Maria de los Angeles Torres Apaza |
| Gestión, requisitos y documentación | Damaritd Camila Mamani Paucar |
| Docente | Ing. Rubén Roque Sucari |
