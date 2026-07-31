# Setup & Installation Guide - Library Management System

This step-by-step guide explains how to set up, configure, and execute the Library Management System on your machine.

---

## 📋 Step 1: Environment Setup

Ensure you have installed:
1. **Java Development Kit (JDK 17 or Java 21)**
   Verify installation:
   ```bash
   java -version
   ```

2. **Apache Maven**
   Verify installation:
   ```bash
   mvn -version
   ```

3. **MySQL Database Server (Optional if using H2 Profile)**
   Ensure MySQL service is active on port `3306`.

---

## 🗄️ Step 2: Database Setup

### Option 1: Using MySQL (Default)

1. Open your MySQL client (MySQL Workbench, phpMyAdmin, or MySQL CLI):
   ```sql
   CREATE DATABASE library_db;
   ```

2. Open `src/main/resources/application.properties` and verify your credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=password
   ```

3. Spring Data JPA with Hibernate DDL `update` mode will automatically generate all necessary tables (`books`, `students`, `issued_books`, `users`) on application launch.

---

### Option 2: Using H2 In-Memory Database (Fast Local Testing)

If MySQL is not installed locally, launch the application using the `h2` Spring profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```
Access H2 Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:library_db`, User: `sa`, Password: empty).

---

## 🔨 Step 3: Build & Execution Commands

### Using Windows PowerShell:

1. **Set JAVA_HOME (Required once per terminal session)**:
   ```powershell
   $env:JAVA_HOME="C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot"
   ```

2. **Run Application (Using bundled Maven)**:
   ```powershell
   .\tools\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
   ```

3. **Build & Package WAR/JAR**:
   ```powershell
   .\tools\apache-maven-3.9.6\bin\mvn.cmd clean package
   ```


---

## 🖥️ Step 4: Accessing Application UI

Open your web browser and navigate to:
- **Dashboard**: `http://localhost:8080/` or `http://localhost:8080/dashboard`
- **Books List**: `http://localhost:8080/books`
- **Add Book**: `http://localhost:8080/books/add`
- **Students Registry**: `http://localhost:8080/students`
- **Add Student**: `http://localhost:8080/students/add`
- **Issue Book**: `http://localhost:8080/issue`
- **Return Book Terminal**: `http://localhost:8080/return`
- **Issued Books Log**: `http://localhost:8080/issued-books`

---

## 🧪 Step 5: Features Testing Walkthrough

1. **Dashboard Overview**:
   - Check the 4 metric cards (`Total Books`, `Available Books`, `Issued Books`, `Total Students`).
   - Notice pre-seeded initial data automatically loaded by `DataInitializer`.

2. **Add a Book**:
   - Click **Add New Book** on the top right.
   - Enter title, author, ISBN, category, publisher, and quantity.
   - Click **Save Book**. Verify redirection and success message.

3. **Register a Student**:
   - Navigate to **Students Registry** -> **Register Student**.
   - Fill in student details and submit.

4. **Issue a Book**:
   - Navigate to **Issue Book**.
   - Select a student from the dropdown.
   - Select an available book.
   - Choose a due date and click **Issue Book Now**.
   - Verify that the available quantity of the selected book decreases by 1 on the books list.

5. **Return a Book**:
   - Navigate to **Return Book**.
   - Locate the issued item and click **Return Book**.
   - Verify that the transaction status changes to `RETURNED` and available stock increments by 1.
