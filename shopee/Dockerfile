# Sử dụng image JDK để chạy ứng dụng Java
FROM openjdk:17-jdk-slim

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Lệnh để chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
