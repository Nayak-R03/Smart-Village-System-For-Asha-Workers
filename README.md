# SVSfAW – Smart Village System for asha workers

## Overview
SVSfAW (Smart Village System for asha workers) is a full-stack Spring Boot web application developed to manage village-level data.  
The system enables management of ASHA workers, families records, announcements, and work schedules through a structured and scalable architecture.

## Key Features
- ASHA Worker Registration & Management
- Creating , Updating, Deleting, View of family records
- State, District, Taluk, Village & Ward Mapping
- Work Schedule Management
- Announcement Module
- Report Retrieval System
- Database integration using JPA/Hibernate

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- Thymeleaf
- MySQL
- Maven

## Architecture
The application follows layered architecture:
- Controller Layer
- Service Layer
- Repository Layer
- Entity/Model Layer
- MVC Pattern

## How to Run Locally

1. Clone the repository:
   git clone https://github.com/Nayak-R03/Smart-Village-System-For-Asha-Workers.git

2. Configure database in:
   src/main/resources/application.properties

3. Import the provided `database_tables.sql` into MySQL

4. Run the application:
   mvn spring-boot:run

The application will start on:
http://localhost:8080

## Author
Ranjitha
GitHub: https://github.com/Nayak-R03
