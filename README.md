# EspigaCloud

Sistema web para la gestión integral de pedidos de la Panificadora Espiga de Trigo.

## Documentación

La documentación funcional, técnica, de calidad y auditoría SDLC está publicada con MkDocs:

**https://rouss07.github.io/EspigaCloud/**

Incluye:

- manual de usuario;
- arquitectura y módulos;
- auditoría SDLC y sus 15 documentos fuente;
- pruebas, JaCoCo, SonarQube y seguridad;
- Jenkins, Docker y CI/CD;
- biblioteca de evidencias descargables.

## Abrir la documentación localmente

```powershell
python -m venv .venv-docs
.\.venv-docs\Scripts\python.exe -m pip install -r requirements-docs.txt
.\.venv-docs\Scripts\python.exe -m mkdocs serve
```

Luego abra `http://127.0.0.1:8000`.

## Ejecutar la aplicación

Con MySQL/MariaDB iniciado:

```powershell
.\mvnw.cmd spring-boot:run
```

Aplicación: `http://localhost:8080`

Jenkins: `http://localhost:8090`

SonarQube: `http://localhost:9000`
