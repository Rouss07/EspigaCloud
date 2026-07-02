# Entorno DevOps de EspigaCloud

Levanta Jenkins LTS sobre JDK 21 y SonarQube Community con datos persistentes. Jenkins compila el proyecto con JDK 17 y Maven 3.9.

Copie `.env.example` como `.env`, defina la contraseña local y ejecute `docker compose up -d --build`.

- Jenkins: http://localhost:8080
- SonarQube: http://localhost:9000

El token de SonarQube se guarda cifrado en el almacén de credenciales de Jenkins.
