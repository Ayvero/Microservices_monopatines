# 🛴 Microservicios - Sistema de Alquiler de Monopatines

Este proyecto es una aplicación backend desarrollada con arquitectura de microservicios. Simula un sistema de alquiler de monopatines eléctricos con múltiples funcionalidades distribuidas en servicios independientes.

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- Docker + Docker Compose
- PostgreSQL
- JPA / Hibernate
- Eureka (Service Discovery)
- Spring Cloud Gateway
- Spring Config Server

## 🧩 Microservicios incluidos

- `microservicio-administrador`: Gestión de administradores del sistema.
- `microservicio-usuario`: Registro, autenticación y gestión de usuarios.
- `microservicio-monopatin`: Información y disponibilidad de monopatines.
- `microservicio-viaje`: Registro y seguimiento de los viajes realizados.
- `microservicio-mantenimiento`: Gestión de mantenimiento de monopatines.
- `microservicio-gateway`: Puerta de entrada al sistema (API Gateway).
- `shared-dto`: Librería común de clases compartidas entre servicios.

## ⚙️ Cómo ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Ayvero/Microservices_monopatines.git
   cd Microservices_monopatines

2. Levantar los servicios con Docker Compose:

docker-compose up --build

3. Acceder a los endpoints a través del gateway en http://localhost:8080

Es necesario tener Docker y Docker Compose instalados previamente.

--------------------------------------


---

## 🌐 English Version 


# 🛴 Microservices - Electric Scooter Rental System

This project is a backend application built using microservices architecture. It simulates an electric scooter rental system, with multiple features distributed across independent services.

## 🚀 Technologies Used

- Java 17
- Spring Boot
- Maven
- Docker + Docker Compose
- PostgreSQL
- JPA / Hibernate
- Eureka (Service Discovery)
- Spring Cloud Gateway
- Spring Config Server

## 🧩 Included Microservices

- `microservicio-administrador`: Administrator management.
- `microservicio-usuario`: User registration, login, and profile.
- `microservicio-monopatin`: Scooter availability and info.
- `microservicio-viaje`: Trip records and tracking.
- `microservicio-mantenimiento`: Maintenance service for scooters.
- `microservicio-gateway`: Entry point (API Gateway).
- `shared-dto`: Common shared classes across services.

## ⚙️ How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/Ayvero/Microservices_monopatines.git
   cd Microservices_monopatines
2. Start the services with Docker Compose:
docker-compose up --build
3.Access the endpoints through the gateway at http://localhost:8080
Make sure Docker and Docker Compose are installed.
-----------------------------------------


   
