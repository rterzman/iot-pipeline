FROM --platform=linux/x86_64 eclipse-temurin:17.0.4.1_1-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar","app.jar"]