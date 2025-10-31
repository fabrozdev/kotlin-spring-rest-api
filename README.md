# Spring Kotlin REST API with MongoDB

A proof-of-concept REST API built with Kotlin, Spring Boot, and MongoDB for rapid prototyping and demonstration purposes.

## Tech Stack

- **Kotlin** 2.2.21 
- **Spring Boot** 3.5.7
- **MongoDB** - NoSQL database
- **Maven** - Dependency management
- **Java** 17

## Features

- RESTful API endpoints
- MongoDB integration for data persistence
- Kotlin-based domain models
- Spring Boot auto-configuration
- Easy setup for quick prototyping

## Prerequisites

Before running this application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+ (running locally or accessible remotely)

## Getting Started

### 1. Start MongoDB

Make sure MongoDB is running:

```bash
# Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 2. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

## Project Structure

```
spring-kotlin-rest-api/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/example/
│   │   │       ├── SpringKotlinRestApiApplication.kt
│   │   │       ├── controller/
│   │   │       │   └── EmployeeController.kt
│   │   │       ├── model/
│   │   │       │   └── Employee.kt
│   │   │       ├── repository/
│   │   │       │   └── EmployeeRepository.kt
│   │   │       └── service/
│   │   │           └── EmployeeService.kt
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── kotlin/
├── pom.xml
└── README.md
```

## Testing

Run tests using:

```bash
mvn test
```

## Development

### Hot Reload

For development with automatic restart on code changes, Spring Boot DevTools is recommended:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

## MongoDB Setup with Docker

For quick setup, use Docker:

```bash
# Run MongoDB container
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=password \
  mongo:latest

# Connection string for authenticated MongoDB
spring.data.mongodb.uri=mongodb://admin:password@localhost:27017/mydb?authSource=admin
```

## Environment Variables

You can override configuration using environment variables:

```bash
export SERVER_PORT=9090
export SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/mydb
mvn spring-boot:run
```

## Proof of Concept Notes

This is a PoC application intended for:
- Rapid prototyping
- Technology evaluation
- Demonstrating REST API concepts
- Learning Kotlin with Spring Boot

## Future Enhancements

- [ ] Add Spring Security for authentication
- [ ] Implement JWT token-based authorization
- [ ] Add input validation with Bean Validation
- [ ] Implement pagination for list endpoints
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Add Docker support with docker-compose
- [ ] Implement integration tests
- [ ] Add logging with SLF4J
- [ ] Error handling and custom exceptions
