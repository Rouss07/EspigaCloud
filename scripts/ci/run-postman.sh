#!/usr/bin/env sh
set -eu

BASE_URL="${1:-http://localhost:8085}"
mkdir -p target/postman

docker run --rm \
  --network host \
  -v "$PWD:/etc/newman" \
  postman/newman:6-alpine \
  run /etc/newman/postman/EspigaPedidos.postman_collection.json \
  --env-var "baseUrl=$BASE_URL" \
  --reporters cli,junit,json \
  --reporter-junit-export /etc/newman/target/postman/newman-report.xml \
  --reporter-json-export /etc/newman/target/postman/newman-report.json
