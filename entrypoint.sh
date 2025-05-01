#!/bin/bash

set -e  # 에러 발생 시 스크립트 종료

# ECS 메타데이터에서 프라이빗 IP 추출
echo "[entrypoint] Fetching container metadata..."
ECS_INSTANCE_IP_ADDRESS=$(curl -s "$ECS_CONTAINER_METADATA_URI" | jq -r '.Networks[0].IPv4Addresses[0]')

if [ -z "$ECS_INSTANCE_IP_ADDRESS" ]; then
  echo "[entrypoint] Failed to retrieve ECS instance IP address."
  exit 1
fi

export ECS_INSTANCE_IP_ADDRESS
echo "[entrypoint] ECS_INSTANCE_IP_ADDRESS resolved as: $ECS_INSTANCE_IP_ADDRESS"

# Java 옵션이 있으면 표시
echo "[entrypoint] JAVA_OPTS: $JAVA_OPTS"

# Spring Boot JAR 실행
exec java "$JAVA_OPTS" -jar /app.jar
