# Metro Backend Application

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.1-blue.svg)](https://spring.io/projects/spring-cloud)

## 📋 Overview

Metro Backend Application is a microservices backend system for the metro management application, developed by the HCMURS organization. The system is designed with a microservices architecture using Spring Boot and Spring Cloud.

## 🏗️ System Architecture

The system is designed with the following microservices:

| Service | Description |
|---------|-------------|
| `eureka-service` | Service discovery for microservices |
| `gateway-service` | API Gateway that manages requests from clients |
| `auth-service` | Authentication and authorization management |
| `user-service` | User information management |
| `station-service` | Station and route information management |
| `ticket-service` | Ticket and booking information management |
| `order-service` | Order and payment management |
| `notification-service` | User notification management |

## 🛠️ Technologies Used

- **Java 21**: Main programming language
- **Spring Boot 3.4.4**: Application development framework
- **Spring Cloud 2024.0.1**: Microservices framework
- **Spring Security**: Authentication and authorization
- **PostgreSQL**: Relational database
- **Redis**: In-memory database and caching
- **JWT**: Token-based authentication
- **Eureka**: Service discovery
- **OpenFeign**: Client-side load balancing and REST client
- **SpringDoc OpenAPI**: API documentation (Swagger)
- **Lombok**: Reduces boilerplate code
- **MapStruct**: Object mapping
- **Docker**: Application containerization

## 🚀 Installation and Running

### System Requirements

- Java 21 or higher
- Maven 3.8 or higher
- Docker and Docker Compose (if running containerized)
- PostgreSQL (if running locally)
- Redis (if running locally)

### Environment Configuration

Create an `.env` file in the root directory and in the `/scripts` directory (if applicable) with the necessary configurations. Refer to the required environment variables for each service.

### Running the Entire Application

#### Using Maven

```bash
mvn clean package
mvn spring-boot:run -pl services/eureka-service
mvn spring-boot:run -pl services/gateway-service
mvn spring-boot:run -pl services/auth-service
# Run other services similarly
```

#### Using Docker

```bash
# Build Docker images for each service
mvn clean package -P docker

# Or build a specific service
mvn clean package -P eureka
mvn clean package -P gateway
mvn clean package -P auth
# etc.

# Use Docker Compose to run the entire system
docker-compose up
```

## 🧪 Testing

```bash
mvn test
```

## 📚 API Documentation

After running the application, you can access the Swagger UI to view the API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## 📦 Project Structure

```
metro-be/
├── services/                  # Directory containing microservices
│   ├── eureka-service/        # Service discovery
│   ├── gateway-service/       # API Gateway
│   ├── auth-service/          # Authentication & Authorization
│   ├── user-service/          # User management
│   ├── station-service/       # Station management
│   ├── ticket-service/        # Ticket management
│   ├── order-service/         # Order management
│   └── notification-service/  # Notification management
├── scripts/                   # Support scripts (if any)
├── .env                       # Environment configuration file
├── docker-compose.yml         # Docker Compose configuration
├── pom.xml                    # Maven parent configuration
└── README.md                  # Project documentation
```

## 🔄 Development Workflow

1. Fork the project
2. Create a new branch for your feature: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Create a Pull Request

## 🤝 Contributing

All contributions are welcome. Please create issues or pull requests to improve the project.

## 📝 License

Copyright © 2025 HCMURS. All rights reserved.

## 👥 Development Team

- **Organization**: HCMURS

## 📞 Contact

If you have any questions, please contact via [GitHub Issues](https://github.com/hcmurs/metro-be/issues).

---

Created on: 2025-06-17
