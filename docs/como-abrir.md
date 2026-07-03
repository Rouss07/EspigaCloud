# Cómo abrir MkDocs

## Opción 1 - Doble clic en Windows

1. Descargue o clone el repositorio EspigaCloud.
2. Abra la carpeta del proyecto.
3. Ejecute `abrir-documentacion.bat` con doble clic.
4. Espere mientras se prepara MkDocs la primera vez.
5. El navegador abrirá `http://127.0.0.1:8000`.

Mantenga abierta la ventana de comandos mientras consulta la documentación. Para detener el servidor, presione `Ctrl + C`.

## Opción 2 - Desde PowerShell

Abra PowerShell dentro de la carpeta EspigaCloud y ejecute:

```powershell
python -m venv .venv-docs
.\.venv-docs\Scripts\python.exe -m pip install -r requirements-docs.txt
.\.venv-docs\Scripts\python.exe -m mkdocs serve
```

Después abra:

```text
http://127.0.0.1:8000
```

## Opción 3 - GitHub Pages

Después de activar Pages desde la rama `gh-pages`, el sitio queda disponible sin instalar nada:

[Abrir documentación pública](https://rouss07.github.io/EspigaCloud/){ .md-button .md-button--primary }

## Configurar GitHub Pages una sola vez

1. Abra el repositorio en GitHub.
2. Ingrese a **Settings**.
3. En el menú izquierdo seleccione **Pages**.
4. En **Source** elija **Deploy from a branch**.
5. Seleccione la rama `gh-pages` y la carpeta `/ (root)`.
6. Presione **Save**.
7. Espere uno o dos minutos y abra el enlace público.

## Video de referencia del curso

[Ver guía proporcionada en YouTube](https://youtu.be/-Dw5dphBAjg?si=kg_oYjDnbOOZgR-x)

La implementación de este repositorio sigue el mismo flujo general: archivo `mkdocs.yml`, contenido Markdown dentro de `docs/`, servidor local con `mkdocs serve` y publicación mediante GitHub Pages.

## Validar antes de publicar

```powershell
.\.venv-docs\Scripts\python.exe -m mkdocs build --strict
```

Si termina sin errores, la navegación y los enlaces internos son válidos.
