# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy full pom.xml and all service folders
COPY pom.xml ./
COPY services/ ./services/

# Build using eureka profile only
RUN mvn -Pauth clean package -DskipTests

# Runtime stage
FROM openjdk:21-slim
WORKDIR /app

COPY --from=build /app/services/auth-service/target/*.jar app.jar

EXPOSE 4006
CMD ["java", "-jar", "app.jar"]
