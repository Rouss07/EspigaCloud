@echo off
setlocal
cd /d "%~dp0"

where python >nul 2>nul
if errorlevel 1 (
  echo No se encontro Python. Instale Python 3 y vuelva a ejecutar este archivo.
  pause
  exit /b 1
)

if not exist ".venv-docs\Scripts\python.exe" (
  echo Preparando el entorno de documentacion...
  python -m venv .venv-docs || goto :error
)

echo Instalando o comprobando MkDocs...
".venv-docs\Scripts\python.exe" -m pip install -q -r requirements-docs.txt || goto :error

echo Abriendo EspigaCloud Docs en http://127.0.0.1:8000
start "" cmd /c "timeout /t 3 /nobreak >nul && start http://127.0.0.1:8000"
".venv-docs\Scripts\python.exe" -m mkdocs serve
exit /b 0

:error
echo No fue posible iniciar la documentacion.
pause
exit /b 1
