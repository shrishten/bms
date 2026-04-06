
# 🎬 BookMyShow Backend (BMS)

A **Spring Boot backend application** that implements core features of the BookMyShow platform such as **movies, theatres, shows, bookings, users, and seat management**, secured using **JWT authentication and role-based authorization**.

---

## 🚀 Features

- 👤 User Registration & Login (JWT Authentication)
- 🔐 Role-based Authorization (`ROLE_ADMIN`, `ROLE_USER`)
- 🎥 Movie Management
- 🏢 Theatre Management
- ⏰ Show Scheduling
- 🎟️ Ticket Booking
- 💺 Seat Selection
- 💰 Price Calculation
- 🧩 DTO-based request/response handling
- 🛡️ Spring Security integration

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- MySQL
- Maven
- Lombok
- Jackson

---

## 📂 Project Structure
src/main/java/com/example/bookmyshow
├── controllers        # REST controllers

├── service            # Business logic

├── repository         # JPA repositories

├── entities           # JPA entities

├── DTO                # Data Transfer Objects

├── enums              # Enums (BookingStatus, Roles)

├── security           # JWT & Security config

├── exception          # Custom exceptions

└── BookMyShowApplication.java

---
##  how to run>

git clone https://github.com/shrishten/bms.git

cd bms

mvn clean install

mvn spring-boot:run
