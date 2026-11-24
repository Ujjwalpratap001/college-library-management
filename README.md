# College Library Management System – Java, Spring Boot & MongoDB

A web-based **College Library Management System** built as a college project. It helps organize a library catalogue, track book availability, issue books to students, and record returns and borrowing history.

This project is maintained by **Ujjwal Pratap**, a B.Tech Computer Science Engineering student at GLA University, Mathura, Uttar Pradesh, and CEO of AP Creative Studio.

## Features

- Dashboard showing book titles, copies, availability, and issued-book counts
- Add, edit, search, and delete catalogue entries
- Issue a book to a student using a college ID or roll number
- Automatic 14-day due-date calculation
- Book return tracking and borrowing history
- Department field for college-specific organization
- Responsive interface for desktop and mobile screens

## Technology stack

- Java 17
- Spring Boot
- MongoDB
- Maven
- HTML, CSS, and JavaScript

## Requirements

- Java 17 or newer
- Maven 3.6+ (or IntelliJ IDEA Maven support)
- MongoDB Community Server, running locally on port 27017

## Run locally

1. Install Java 17+, Maven, and MongoDB.
2. Start MongoDB.
3. Check the connection setting in `src/main/resources/application.properties`. The documented local database URI is:

   ```text
   mongodb://localhost:27017/college_library
   ```

4. Open the repository as a Maven project in IntelliJ IDEA, or use a terminal:

   ```bash
   mvn spring-boot:run
   ```

5. Open [http://localhost:8080](http://localhost:8080).

For MongoDB Atlas, configure `spring.data.mongodb.uri` with your own connection string. Never commit real passwords or credentials.

## Main API endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/dashboard` | Dashboard data |
| GET | `/api/books` | List books |
| GET | `/api/books?search=java` | Search books |
| POST | `/api/books` | Add a book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |
| GET | `/api/records` | Borrowing records |
| POST | `/api/issue/{bookId}` | Issue a book |
| POST | `/api/return/{recordId}` | Return a book |

## Security and project status

This is a college-project starter version. It does not currently document authentication or role-based permissions, fine calculation, email reminders, or audit logging. Add authentication and server-side authorization before using real student records. Use sample data for demonstrations.

## About the Developer

**Ujjwal Pratap** is a B.Tech CSE student at GLA University, Mathura, and CEO of AP Creative Studio. His project work focuses on practical software, web applications, and learning through implementation.

- GitHub: [Ujjwalpratap001](https://github.com/Ujjwalpratap001)
- LinkedIn: [Ujjwal Pratap](https://www.linkedin.com/in/ujjwalpratap624604/)
- Personal portfolio: [ujjwal-portfolio](https://github.com/Ujjwalpratap001/ujjwal-portfolio)
