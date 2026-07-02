#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/k6

for script in pruebasK6/*/smoke-test.js; do
  name="$(basename "$(dirname "$script")")"
  echo "Ejecutando k6 smoke: $name"
  docker run --rm \
    --network host \
    -e BASE_URL="$BASE_URL" \
    -v "$PWD:/workspace" \
    -w /workspace \
    grafana/k6:0.49.0 \
    run --summary-export "target/k6/${name}-summary.json" "$script"
done
