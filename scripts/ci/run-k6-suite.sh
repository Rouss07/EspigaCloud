#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/k6

if [ -d /var/jenkins_home ] && [ -n "${JENKINS_URL:-}" ]; then
  image="espigacloud-k6:${BUILD_NUMBER:-local}"
  docker build -f scripts/ci/Dockerfile.k6 -t "$image" .
fi

for script in pruebasK6/*/smoke-test.js; do
  name="$(basename "$(dirname "$script")")"
  echo "Ejecutando k6 smoke: $name"
  if [ -n "${image:-}" ]; then
    container="espigacloud-k6-${BUILD_NUMBER:-local}-$name"
    docker rm -f "$container" >/dev/null 2>&1 || true
    status=0
    docker run --name "$container" --network host -e BASE_URL="$BASE_URL" "$image" \
      run --summary-export "target/k6/${name}-summary.json" "$script" || status=$?
    docker cp "$container:/workspace/target/k6/${name}-summary.json" "target/k6/${name}-summary.json" || true
    docker rm -f "$container" >/dev/null
    [ "$status" -eq 0 ] || exit "$status"
  else
    docker run --rm \
      --network host \
      -e BASE_URL="$BASE_URL" \
      -v "$PWD:/workspace" \
      -w /workspace \
      grafana/k6:0.49.0 \
      run --summary-export "target/k6/${name}-summary.json" "$script"
  fi
done
