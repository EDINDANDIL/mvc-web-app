# Employee Management System - Spring MVC Web Application

This project demonstrates a modern web application for employee management built with Spring MVC, Thymeleaf and PostgreSQL. It implements core Spring features with clean architecture.

## Features
* Full CRUD operations for employee management
* PostgreSQL database integration with Spring Data JPA
* Thymeleaf templates with Bootstrap styling
* Form validation (server-side and client-side)
* Pagination and sorting of employee data
* Logging with SLF4J and Logback

## Technologies
* Spring Boot 3.1
* Spring MVC
* Spring Data JPA
* Thymeleaf template engine
* PostgreSQL 15
* Bootstrap 5
* Maven
* Lombok
* MapStruct (for DTO mapping)

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3.9+
* PostgreSQL 15+

### Installation Steps:

1. Clone the repository:
```bash
git clone https://github.com/EDINDANDIL/mvc-web-app.git
cd mvc-web-app
```
2. Set up your database
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_management
spring.datasource.username=your_username
spring.datasource.password=your_password
```
