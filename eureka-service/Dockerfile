FROM openjdk:21-jdk-slim
EXPOSE 19090
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]