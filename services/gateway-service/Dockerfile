# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the parent pom.xml first
COPY pom.xml ./
# Copy the gateway-service module
COPY gateway-service/ ./gateway-service/

# Go to gateway-service directory and download dependencies
WORKDIR /app/gateway-service
RUN mvn dependency:go-offline

# Build the project
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/gateway-service/target/gateway-service-1.0.0.jar app.jar

# Add wait-for-it script to ensure database is ready
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080
# Wait for MySQL to be ready before starting the application
CMD ["java", "-jar", "app.jar"]