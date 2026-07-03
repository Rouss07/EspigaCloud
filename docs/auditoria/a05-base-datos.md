# A05 - Base de datos

## Alcance

Se revisaron entidades, repositorios, relaciones JPA, conexión MySQL/MariaDB y creación del entorno temporal para pruebas.

## Resultado

**Cumple parcialmente.** La persistencia está integrada y probada, pero falta formalizar respaldos, restauración y migraciones versionadas para producción.

## Controles existentes

- credenciales configurables por variables de entorno;
- base temporal aislada en CI;
- repositorios sometidos a pruebas de integración;
- volúmenes Docker persistentes para despliegues locales.

## Acciones recomendadas

1. incorporar Flyway o Liquibase;
2. automatizar respaldos cifrados;
3. probar restauración periódicamente;
4. separar usuarios y privilegios por ambiente.
