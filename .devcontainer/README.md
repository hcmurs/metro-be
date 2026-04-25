# Devcontainer For Metro Backend

This directory contains the devcontainer configuration for the `metro-be` repository. Its purpose is to provide a consistent development environment for this Spring Boot microservices project with Java 21, Maven, and Docker Compose ready to use.

## Main File

- `devcontainer.json`: defines the container environment used by VS Code, GitHub Codespaces, or the Dev Containers CLI when opening this repository.

## Base Environment

The devcontainer uses this image:

```text
mcr.microsoft.com/devcontainers/java:1-21-bookworm
```

This image fits the repository because the project uses:

- Java 21
- Spring Boot 3.4.4
- Spring Cloud 2024.0.1
- Maven
- Docker Compose for supporting services and microservices

## Installed Features

### Maven 3.9.9

```json
"ghcr.io/devcontainers/features/maven:1": {
  "version": "3.9.9"
}
```

This feature installs Maven inside the container. The repository still includes Maven Wrapper scripts (`mvnw`), but having Maven available globally is convenient for commands such as:

```bash
mvn test
mvn clean package
mvn spring-boot:run -pl services/eureka-service
```

### Docker Outside Of Docker

```json
"ghcr.io/devcontainers/features/docker-outside-of-docker:1": {}
```

This feature allows the container to use the host Docker daemon. That means Docker Compose can be run directly from inside the devcontainer:

```bash
docker compose up
docker compose up postgresql redis
docker compose down
```

This matches the repository layout because the root directory already contains `docker-compose.yml`.

## Container User

```json
"remoteUser": "vscode"
```

The devcontainer runs as the `vscode` user, which is the default user for Dev Containers. It is appropriate for day-to-day development and avoids running everything as `root`.

## Environment Variables

```json
"containerEnv": {
  "JAVA_HOME": "/usr/local/sdkman/candidates/java/current",
  "SPRING_PROFILES_ACTIVE": "dev"
}
```

These variables:

- Point `JAVA_HOME` to the JDK managed by the image.
- Set the default Spring profile to `dev` when services are run manually inside the container.

Note: when services are run with `docker compose`, they may still use profiles declared in `docker-compose.yml`, such as `docker`.

## Maven Dependency Cache

```json
"mounts": [
  "source=metro-be-m2,target=/home/vscode/.m2,type=volume"
]
```

This volume caches the Maven local repository at `.m2`. Dependencies do not need to be downloaded again every time the devcontainer is rebuilt.

This is useful because the repository contains multiple Maven services:

- `eureka-service`
- `gateway-service`
- `auth-service`
- `user-service`
- `station-service`
- `ticket-service`
- `order-service`
- `notification-service`
- `cronjob-service`

## Forwarded Ports

The devcontainer forwards the ports commonly used by this repository:

| Port | Purpose |
| --- | --- |
| `4003` | Gateway Service |
| `4004` | Station Service |
| `4005` | Ticket Service |
| `4006` | Auth Service |
| `4007` | User Service |
| `4008` | Order/Notification Service |
| `4009` | Order/Notification Service |
| `4010` | Cronjob Service |
| `5050` | pgAdmin |
| `5432` | PostgreSQL |
| `5672` | RabbitMQ |
| `6379` | Redis |
| `8761` | Eureka |

The labels in `portsAttributes` help VS Code display readable names for each forwarded port.

## VS Code Extensions

The devcontainer recommends or installs these extensions:

- `vscjava.vscode-java-pack`: Java extension pack
- `vmware.vscode-spring-boot`: Spring Boot support
- `vscjava.vscode-spring-initializr`: Spring project support
- `redhat.vscode-yaml`: YAML support
- `humao.rest-client`: runs `.http` files in the `http/` directory
- `ms-azuretools.vscode-docker`: Docker support

## VS Code Settings

```json
"java.configuration.updateBuildConfiguration": "automatic"
```

The Java extension automatically updates the build configuration when Maven changes are detected.

```json
"java.compile.nullAnalysis.mode": "automatic"
```

The Java extension enables null-safety analysis automatically when appropriate.

```json
"maven.executable.path": "/usr/local/sdkman/candidates/maven/current/bin/mvn"
```

This points VS Code to the Maven installation inside the container.

```json
"rest-client.environmentVariables": {
  "$shared": {
    "gatewayUrl": "http://localhost:4003"
  }
}
```

This defines a shared REST Client variable. When writing or running `.http` files, the default Gateway URL can be referenced as `http://localhost:4003`.

## Post-Create Command

```json
"postCreateCommand": "chmod +x mvnw format-all.sh spotless.sh setup-hooks.sh services/*/mvnw services/*/format.sh && if [ ! -f .env ] && [ -f .env.sample ]; then cp .env.sample .env; fi && mvn -version"
```

This command runs once after the devcontainer is created:

1. Makes important scripts executable:
   - `mvnw`
   - `format-all.sh`
   - `spotless.sh`
   - `setup-hooks.sh`
   - `services/*/mvnw`
   - `services/*/format.sh`
2. Creates `.env` from `.env.sample` if `.env` does not exist.
3. Prints `mvn -version` to confirm Maven is available.

## Post-Start Command

```json
"postStartCommand": "docker compose version"
```

This command checks whether Docker Compose is available inside the container.

## Post-Attach Command

```json
"postAttachCommand": "git status --short"
```

This command prints a short Git status every time VS Code attaches to the container.

## Usage

### Open With VS Code

1. Install the `Dev Containers` extension.
2. Open this repository in VS Code.
3. Select `Reopen in Container`.

### Build The Whole Repository

```bash
mvn clean package
```

### Run Tests

```bash
mvn test
```

Or test one service:

```bash
mvn clean test -f services/user-service/pom.xml -Dspring.profiles.active=test
```

### Run One Service With Maven

Run Eureka:

```bash
mvn spring-boot:run -pl services/eureka-service
```

Run Gateway:

```bash
mvn spring-boot:run -pl services/gateway-service
```

### Run With Docker Compose

Run the whole stack declared in `docker-compose.yml`:

```bash
docker compose up
```

Run only PostgreSQL and Redis:

```bash
docker compose up postgresql redis
```

Stop the stack:

```bash
docker compose down
```

## Why Services Are Not Built Or Started Automatically

This repository is a microservices system with multiple services and dependencies. Automatically building or starting everything when the devcontainer opens could make startup slow and resource-heavy.

For that reason, the devcontainer prepares the required tools but leaves service startup to the developer. You can run an individual service or use Docker Compose depending on the task.

