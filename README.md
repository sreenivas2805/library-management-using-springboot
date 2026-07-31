# Library Management System

A production-ready, full-stack **Library Management System** built with **Spring Boot 3.x** and **Thymeleaf**, styled with **Bootstrap 5**. Designed with clean MVC architecture, Spring Data JPA, Jakarta Bean Validation, dynamic metrics dashboard, and sample data auto-initialization.

---

## 🌟 Key Features

- 📊 **Interactive Dashboard**: Real-time metric cards for `Total Books`, `Available Books`, `Issued Books`, and `Total Students` plus active book loans summary.
- 📚 **Books Management (CRUD)**: Full catalog management including Title, Author, ISBN, Category, Publisher, Total Quantity, Available Stock, and automatic status updates (`AVAILABLE` vs `OUT_OF_STOCK`).
- 🎓 **Students Registry (CRUD)**: Complete student records with Roll Number, Department, Academic Year, Email, and Phone.
- 🔄 **Book Issue & Return**:
  - Issue books to registered students with custom due dates.
  - Automatic inventory quantity decrement on issue and increment on return.
  - Tracking of active, returned, and overdue book loans.
- 🔍 **Real-Time & Server Search**: Multi-field search for books, students, and issue transaction histories.
- 🛠️ **REST API Support**: Included RESTful endpoints under `/api/` for optional client/mobile integration.
- 💡 **Auto-Seeded Sample Data**: Includes an automated startup initializer (`DataInitializer`) that seeds 5 sample books, 4 students, and active issued transactions for immediate out-of-the-box demoing.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| **Backend Framework** | Spring Boot 3.2.5 (Java 17 / 21) |
| **Data Access** | Spring Data JPA (Hibernate ORM) |
| **Validation** | Jakarta Bean Validation (`@NotBlank`, `@Email`, `@Min`, `@Pattern`) |
| **Template Engine** | Thymeleaf 3.x with layout fragments |
| **Frontend Styling** | Bootstrap 5.3 + Bootstrap Icons + Custom Modern CSS (`#2563EB` theme) |
| **Database** | MySQL 8.x (with H2 in-memory profile support) |
| **Build Tool** | Apache Maven |

---

## 📁 Project Structure

```
d:/sreeni/
├── pom.xml
├── README.md
├── SETUP_GUIDE.md
└── src/
    ├── main/
    │   ├── java/com/library/management/
    │   │   ├── config/             # DataInitializer startup runner
    │   │   ├── controller/         # Spring MVC & REST Controllers
    │   │   ├── dto/                # Validation DTOs & Stat DTOs
    │   │   ├── entity/             # JPA Entities (Book, Student, IssuedBook, User)
    │   │   ├── exception/          # Custom exceptions & GlobalExceptionHandler
    │   │   ├── repository/         # Spring Data JPA Repositories
    │   │   ├── service/            # Service Interfaces & Implementations
    │   │   └── LibraryManagementApplication.java
    │   └── resources/
    │       ├── application.properties    # MySQL Configuration
    │       ├── application-h2.properties # In-Memory H2 Fallback Profile
    │       ├── static/
    │       │   ├── css/style.css   # Custom CSS Theme
    │       │   └── js/main.js      # JS Helpers & Table Filters
    │       └── templates/
    │           ├── fragments/      # Navbar, Sidebar, Footer
    │           ├── books/          # List, Add, Edit templates
    │           ├── students/       # List, Add, Edit templates
    │           ├── issue-return/   # Issue, Return, Issued List templates
    │           ├── dashboard.html  # Main Dashboard
    │           └── error/404.html  # Custom Error Page
```

---

## ⚙️ Quick Start Guide

### 1. Prerequisites
- **JDK 17 or Java 21** installed.
- **Apache Maven** installed.
- **MySQL Server** running on `localhost:3306` (or use H2 profile below).

### 2. Database Setup (MySQL)
Create a MySQL database named `library_db`:
```sql
CREATE DATABASE library_db;
```

Update `src/main/resources/application.properties` with your credentials if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password
```

### 3. Run Application

#### Option A: Running with MySQL
```bash
mvn clean package
mvn spring-boot:run
```

#### Option B: Running with H2 In-Memory Database (No MySQL required)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### 4. Access the App
Open your browser and navigate to:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 🌐 API Endpoints Overview

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/books` | Get all books or filter by `?query=` |
| `GET` | `/api/books/{id}` | Get book by ID |
| `POST` | `/api/books` | Create a new book |
| `PUT` | `/api/books/{id}` | Update book details |
| `DELETE` | `/api/books/{id}` | Delete book record |
| `GET` | `/api/students` | Get all students or filter by `?query=` |
| `POST` | `/api/students` | Register a new student |
| `POST` | `/api/issue` | Issue a book to a student |
| `POST` | `/api/return/{id}` | Mark issued book as returned |
