#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/playwright

if [ -d /var/jenkins_home ] && [ -n "${JENKINS_URL:-}" ]; then
  image="espigacloud-playwright:${BUILD_NUMBER:-local}"
  container="espigacloud-playwright-${BUILD_NUMBER:-local}"
  docker build -f qa/playwright/Dockerfile.ci -t "$image" .
  docker rm -f "$container" >/dev/null 2>&1 || true

  status=0
  docker run --name "$container" \
    --network host \
    -e CI=true \
    -e BASE_URL="$BASE_URL" \
    -e ADMIN_PASSWORD="${ADMIN_PASSWORD:-Admin123*}" \
    "$image" || status=$?

  docker cp "$container:/workspace/target/playwright/." target/playwright/ || true
  docker rm -f "$container" >/dev/null
  exit "$status"
fi

docker run --rm \
  --network host \
  -e BASE_URL="$BASE_URL" \
  -e CI=true \
  -e ADMIN_PASSWORD="${ADMIN_PASSWORD:-Admin123*}" \
  -v "$PWD:/workspace" \
  -w /workspace \
  mcr.microsoft.com/playwright:v1.45.0-jammy \
  sh -c "npm init -y >/dev/null 2>&1 || true; npm install --no-save @playwright/test@1.45.0 >/dev/null; npx playwright test qa/playwright --config=qa/playwright/playwright.config.js"
