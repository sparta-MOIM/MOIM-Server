FROM openjdk:21-jdk-slim
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# 필수 도구 설치
RUN apt-get update && apt-get install -y curl jq

# entrypoint 스크립트 복사
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]