#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/playwright

docker run --rm \
  --network host \
  -e BASE_URL="$BASE_URL" \
  -e ADMIN_PASSWORD="${ADMIN_PASSWORD:-Admin123*}" \
  -v "$PWD:/workspace" \
  -w /workspace \
  mcr.microsoft.com/playwright:v1.45.0-jammy \
  sh -c "npm init -y >/dev/null 2>&1 || true; npx playwright test qa/playwright --config=qa/playwright/playwright.config.js"
