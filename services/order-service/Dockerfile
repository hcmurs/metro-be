# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy service files
COPY services/order-service/pom.xml ./
COPY services/order-service/src ./src

# Build
RUN mvn clean package -DskipTests

# Extract layers for better caching
RUN mkdir -p extracted && \
    java -Djarmode=layertools -jar target/*.jar extract --destination extracted

# Stage 2: Runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy layers
COPY --from=builder /app/extracted/dependencies/ ./
COPY --from=builder /app/extracted/spring-boot-loader/ ./
COPY --from=builder /app/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/extracted/application/ ./

# Install curl for health checks
RUN apk add --no-cache curl

EXPOSE 4008
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]