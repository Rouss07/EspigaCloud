# A03 - Arquitectura

## Arquitectura evaluada

EspigaCloud implementa un monolito modular en capas: presentación, controladores, servicios, repositorios y persistencia. Spring Security funciona como control transversal.

## Resultado

**Cumple parcialmente.** El código mantiene separación de responsabilidades y dependencias claras, pero el expediente original no incluía diagramas UML/C4 suficientes.

## Evidencia actual

- estructura de paquetes del proyecto;
- [descripción técnica y diagrama](../arquitectura.md);
- entidades JPA y relaciones;
- configuración de seguridad.

## Acción recomendada

Mantener diagramas de contexto, contenedores, componentes y modelo de datos alineados con cada versión relevante.
