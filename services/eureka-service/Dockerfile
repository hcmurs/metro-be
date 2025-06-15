# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy full pom.xml and all service folders
COPY pom.xml ./
COPY services/ ./services/

# Build using eureka profile only
RUN mvn -Peureka clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/services/eureka-service/target/*.jar app.jar

EXPOSE 8761
CMD ["java", "-jar", "app.jar"]
