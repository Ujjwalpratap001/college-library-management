# Campus Library — Java + MongoDB

A college-library management website built with Java 17, Spring Boot, MongoDB, HTML, CSS and JavaScript.

## Features
- Dashboard with book-title, copy, availability and issued-book counts
- Add, edit, search and delete catalogue entries
- Issue a book to a student using college ID/roll number
- Automatic 14-day due date
- Return tracking and borrowing history
- Department field for college-specific organization
- Responsive layout for desktop and mobile

## Requirements
- Java 17 or newer
- Maven 3.6+ (or use IntelliJ IDEA's Maven support)
- MongoDB Community Server running locally on port 27017

## Run on Windows
1. Install Java 17+ and MongoDB Community Server.
2. Start MongoDB. The default connection in `src/main/resources/application.properties` is `mongodb://localhost:27017/college_library`.
3. Open this folder in IntelliJ IDEA as a Maven project.
4. Wait for Maven dependencies to download.
5. Run `LibraryApplication.java`, or open a terminal in this folder and run `mvn spring-boot:run`.
6. Open **http://localhost:8080** in your browser.

If you use MongoDB Atlas, replace `spring.data.mongodb.uri` in `application.properties` with your Atlas connection string. Do not commit real database passwords to a public repository.

## Main API endpoints
- `GET /api/dashboard`
- `GET /api/books` and `GET /api/books?search=java`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`
- `GET /api/records`
- `POST /api/issue/{bookId}`
- `POST /api/return/{recordId}`

## Important project note
This is a college-project starter version. It does not yet include authentication/role permissions, fine calculation, email reminders, or audit logging. Add authentication and server-side authorization before using it for real student records. Use sample student data for a classroom demo.
