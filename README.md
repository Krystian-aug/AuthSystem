# Authentication and Authorization System in Spring Boot

This project is a backend authentication and authorization system implemented using **Spring Boot** and **Spring Security**. It provides secure user registration, login, and role-based access control with JWT-based authentication.

## Features

- **User Registration and Login**: Secure user registration and login with password validation and encryption using **BCrypt**.
- **JWT Authentication**: Generation of **JWT** tokens for user authentication and authorization.
- **Custom UserDetails and UserDetailsService**: Custom implementation of Spring Security's `UserDetails` and `UserDetailsService` for user management.
- **RESTful API**: Exposes a REST API for user management following RESTful principles.
- **Exception Handling**: Consistent exception handling throughout the application.
- **Unit Tests**: Unit tests for registration and login logic using **JUnit** and **Mockito** to ensure code reliability.
- **Cloud Deployment**: Deployed to **Google Cloud Platform (GCP)**.
- **PostgreSQL Database**: PostgreSQL database hosted in **Google Cloud SQL** for persistent data storage.

## Tech Stack

- **Java**: Programming language used for the backend.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: Used for securing the application and handling authentication and authorization.
- **Spring Data JPA**: ORM tool for interacting with the database.
- **JWT**: For token-based authentication.
- **PostgreSQL**: Database for storing user data.
- **Google Cloud Platform**: Cloud service used for deploying the application.
- **JUnit** & **Mockito**: For unit testing and mocking.
- **Lombok**: To reduce boilerplate code.
- **Maven**: Build and dependency management tool.
- **IntelliJ IDEA**: IDE used for development.

## Future Development

- **Room Reservation Module**: A module for booking rooms with schedules and availability management.
- **User Roles**: Role-based access control with roles such as Admin, User, and Reception.
- **Email Notifications**: Integration for email notifications and calendar integrations.
- **Full Office Space Management**: Expand the application into a full-fledged office space management system.
