#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/security

docker run --rm \
  --network host \
  -v "$PWD/target/security:/zap/wrk" \
  ghcr.io/zaproxy/zaproxy:stable \
  zap-baseline.py \
  -t "$BASE_URL" \
  -r zap-report.html \
  -J zap-report.json \
  -x zap-report.xml
