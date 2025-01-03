# Banking Management System  

## Overview  

This project is a **Banking Management System** built using **Spring Boot**. It consists of two microservices: one for managing user accounts and another for blocking users. The system is integrated with **Eureka Server** for service discovery and utilizes **load balancing** to ensure high availability. The database is managed using a **PostgreSQL** image in **Docker**.  

## Project Structure  

The project is organized into multiple modules:  

### Modules  

1. **User Service**  
   - **Controllers**:   
     - `AuthenticationController`: Manages user authentication.  
     - `UserController`: Handles user-related operations.  
   - **Services**:  
     - `UserService`: Contains business logic for user management.  
     - `AuthenticationService`: Handles authentication processes.  
   - **Model**:   
     - `User`: Represents user entity.  
   - **Repository**:  
     - `UserRepository`: Interface for database operations related to users.  
   - **Exceptions**: Custom exceptions for error handling.  
   - **DTO**:  
     - `UserDTO`: A data transfer object that facilitates safe data transmission between layers.  
   - **Enums**:  
     - `Rule`: Defines user subscription levels:  
       - **GOLDEN_SUBSCRIPTION**: Premium users with the highest privileges.  
       - **SHABAB**: Special benefits for younger users.  
       - **REGULAR**: Standard user with basic privileges.  
   - **Security**: Integrated using Spring Security to manage user authentication and authorization, ensuring data integrity and controlled access.  

2. **Blocked Users Service**  
   - **Controllers**:   
     - `BlockedUsersController`: Manages operations related to blocked users.  
   - **Services**:  
     - `BlockedUsersService`: Contains business logic for blocking users.  
   - **Model**:   
     - `BlockedUsers`: Represents blocked user entity.  
   - **Repository**:  
     - `BlockedUsersRepository`: Interface for database operations related to blocked users.  
   - **Exceptions**: Custom exceptions for error handling.  

3. **Eureka Server**  
   - Handles service registration and discovery.  

## Technologies Used  

- **Spring Boot**: Framework for building microservices.  
- **PostgreSQL**: Relational database for data storage.  
- **Docker**: Containerization for the PostgreSQL database.  
- **Spring Cloud Netflix Eureka**: For service discovery.  
- **Spring Data JPA**: For database interaction.  
- **JUnit**: For testing.  

## Prerequisites  
- Java 22  
- Docker  
- Gradle  

## Build Configuration  

The project utilizes a root `build.gradle` file that acts as a common configuration for all microservices. This file defines the project structure, dependency management, and build settings for the entire application.  

### Key Features of the Root `build.gradle`:  

- **Plugins**:   
  - Applies the `java` plugin for Java projects and the `io.spring.dependency-management` plugin to manage dependencies effectively.  

- **Group and Version**:   
  - Defines the group ID and version of the project, ensuring consistency across all microservices.  

- **Java Toolchain**:   
  - Specifies the Java version (22) to be used for building the project, ensuring compatibility and enabling the use of the latest Java features.  

- **Subprojects Configuration**:   
  - The `subprojects` block applies the same configuration to all included microservices (`user-service`, `blocked-users`, and `eureka-server`), reducing redundancy.  

- **Dependency Management**:  
  - Imports managed dependencies using BOM (Bill of Materials) for both Spring Boot and Spring Cloud, allowing for version consistency and easy upgrades.  
- **Repositories**:   
  - The repository is set to `mavenCentral()`, from which dependencies are resolved.  

- **Lombok**:   
  - Includes `Lombok` for simplifying Java code by reducing boilerplate code.  

- **Unit Testing**:   
  - Configures tests to use the JUnit platform.  

## Spring Cloud Integration  

This project integrates **Spring Cloud**, a suite of tools that enhances the development of cloud-native applications. Specifically:  

- **Service Discovery**: Leverages **Spring Cloud Netflix Eureka**, allowing microservices to register themselves with a central server, facilitating seamless service discovery.  

- **Load Balancing**: Spring Cloud also facilitates load balancing among service instances, improving performance and reliability by distributing requests across multiple service instances.  

## Microservices Dependencies  

### User Service  
- Spring Boot Starter Web  
- Spring Cloud Netflix Eureka Client  
- Spring Boot Starter Validation  
- Spring Boot Starter Security  
- Spring Boot Starter Data JPA  
- PostgreSQL Driver  
- JUnit 5  

### Blocked Users Service  
- Spring Boot Starter Web  
- Spring Boot Starter Data JPA  
- Spring Cloud Netflix Eureka Client  
- PostgreSQL Driver  
- JUnit 5
