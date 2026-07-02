#!/usr/bin/env sh
set -eu

URL="${1:-http://localhost:8085/login}"
ATTEMPTS="${WAIT_ATTEMPTS:-60}"

i=1
while [ "$i" -le "$ATTEMPTS" ]; do
  if curl -fsS "$URL" >/dev/null; then
    echo "OK: $URL disponible"
    exit 0
  fi
  echo "Esperando $URL ($i/$ATTEMPTS)"
  i=$((i + 1))
  sleep 5
done

echo "ERROR: $URL no respondio a tiempo"
exit 1
