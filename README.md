🎬 BookMyShow Backend (BMS)

A Spring Boot–based backend application that replicates core features of the BookMyShow platform, including movie shows, theatres, bookings, users, and seat management, with JWT-based authentication and role-based authorization.

🚀 Features
👤 User Registration & Authentication (JWT + Spring Security)
🔐 Role-based Access Control (ADMIN, USER)
🎥 Movie Management
🏢 Theatre & Screen Management
⏰ Show Scheduling
🎟️ Ticket Booking System
💺 Seat Selection & Validation
💰 Dynamic Price Calculation
🔄 RESTful APIs with DTO-based requests
🛡️ Secure endpoints using Spring Security
🛠️ Tech Stack
Java 17+
Spring Boot
Spring Data JPA (Hibernate)
Spring Security + JWT
MySQL
Maven
Jackson (JSON Serialization)
Lombok
📂 Project Structure
bms/
├── controllers/
├── service/
├── repository/
├── entities/
├── DTO/
├── security/
├── enums/
├── exception/
└── BookMyShowApplication.java
⚙️ Configuration
1️⃣ Database Configuration (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
🔐 Authentication & Authorization
JWT-based authentication
Roles:
ROLE_ADMIN
ROLE_USER
Protected Endpoints Example
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/createshow")
📌 API Overview
🔑 Auth APIs
POST /api/auth/register
POST /api/auth/login
🎬 Movie APIs
POST /api/movie/create
GET /api/movie/all
🏢 Theatre APIs
POST /api/theatre/create
GET /api/theatre/all
⏰ Show APIs
POST /api/show/createshow
GET /api/show/getallshows
GET /api/show/getshowsbymovie
GET /api/show/getshowsbytheatre
🎟️ Booking APIs
POST /api/booking/create
GET /api/booking/user/{userId}
GET /api/booking/show/{showId}
📥 Sample Booking Request (DTO)
{
  "bookingTime": "2026-03-26T12:15:00",
  "status": "CONFIRMED",
  "totalAmount": 8000,
  "numberOfSeats": 2,
  "seatNumbers": ["B4", "B5"],
  "userId": 2,
  "showId": 1
}
🧠 Key Design Decisions
DTOs used to prevent recursive JSON responses
Bidirectional JPA mappings handled with proper serialization control
Business logic separated into service layer
Entity relationships
One Theatre → Many Shows
One Show → Many Bookings
One User → Many Bookings
🧪 Future Enhancements
✅ Seat locking & concurrency handling
💳 Payment Gateway integration
📊 Admin dashboard
🎞️ Movie categories & languages
🧾 Booking history & cancellation refunds
🧑‍💻 Author

Shrish Dutta
Backend Developer | Java | Spring Boot

GitHub: https://github.com/shrishten

⭐ Contributing

Contributions are welcome!
Fork the repo → Create a feature branch → Submit a PR 🚀
