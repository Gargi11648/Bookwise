# 📚 Bookwise – Book Recommendation System

**Bookwise** is a full-stack web application that helps users discover books based on their interests and reading preferences. Users can explore books, search by title, author, or genre, rate books, and receive personalized book recommendations.

The application demonstrates client-server-database connectivity using **Java, Spring Boot, JDBC, Hibernate, and MySQL**.

## ✨ Features

- 📖 **Browse Books:** Explore the available collection of books.
- 🔍 **Search Books:** Search for books by title, author, or genre.
- 🎯 **Personalized Recommendations:** Get recommendations based on previously rated books.
- ⭐ **Rate Books:** Give books ratings from 1 to 5 stars.
- 👤 **Reader Management:** Add new readers to the application.
- ➕ **Add New Books:** Add books with their title, author, genre, publication year, and description.
- 🗂️ **Genre-Based Filtering:** Discover books belonging to different genres.
- 🛢️ **Database Connectivity:** Connect the application to MySQL or use an H2 in-memory database for testing.
- 📊 **Database Status:** Display database connection information using JDBC.
- 📱 **Responsive Interface:** Use the application through a clean, interactive web interface.

## 🛠️ Technologies Used

### Frontend
- HTML5
- CSS3
- JavaScript

### Backend
- Java 17+
- Spring Boot
- Spring Boot REST API
- Spring Data JPA
- Hibernate ORM
- JDBC

### Database
- MySQL
- H2 Database (optional)

### Development Tools
- Apache Maven
- Visual Studio Code or IntelliJ IDEA
- Git and GitHub

## 🏗️ System Architecture

The application follows a client-server architecture.

```text
        User
         |
         v
  Frontend Interface
   HTML, CSS, JavaScript
         |
         v
  Spring Boot REST API
         |
         v
   +-----+------+
   |            |
   v            v
Hibernate      JDBC
   |            |
   +------+-----+
          |
          v
     Database
    MySQL / H2
```

### How It Works

1. The user interacts with the Bookwise website.
2. The frontend sends HTTP requests to the Spring Boot REST API.
3. Hibernate manages database operations for books, readers, and ratings.
4. JDBC executes SQL queries for book recommendations and database connection information.
5. The database returns the requested information.
6. The frontend displays books and recommendations to the user.

## 📂 Project Structure

```text
book-recommender/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── bookrec/
│       │           ├── BookRecommenderApplication.java
│       │           │
│       │           ├── config/
│       │           │   └── DataSeeder.java
│       │           │
│       │           ├── controller/
│       │           │   └── ApiController.java
│       │           │
│       │           ├── dao/
│       │           │   └── RecommendationJdbcDao.java
│       │           │
│       │           ├── model/
│       │           │   ├── AppUser.java
│       │           │   ├── Book.java
│       │           │   └── Rating.java
│       │           │
│       │           └── repository/
│       │               ├── BookRepository.java
│       │               ├── UserRepository.java
│       │               └── RatingRepository.java
│       │
│       └── resources/
│           ├── static/
│           │   └── index.html
│           ├── application.properties
│           └── application-h2.properties
│
├── pom.xml
├── schema.sql
└── README.md
```

## ⚙️ Prerequisites

Before running the project, install the following:

- Java Development Kit (JDK) 17 or later
- Apache Maven
- MySQL Server (optional if using H2)
- A web browser
- An IDE such as Visual Studio Code or IntelliJ IDEA

Verify your Java and Maven installations:

```bash
java -version
mvn -version
```

## 🚀 Installation and Setup

### Step 1: Clone the Repository

Clone your GitHub repository:

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

Navigate to the project directory:

```bash
cd book-recommender
```

Replace `YOUR_GITHUB_REPOSITORY_URL` with your actual GitHub repository URL.

### Step 2: Configure the Database

**Option A: Use MySQL**

1. Install and start MySQL Server.
2. Open the file:

   `src/main/resources/application.properties`

3. Configure your MySQL username and password.

The default configuration is:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/bookrec_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS:}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

server.port=8080
```

The application is configured to create the `bookrec_db` database if it does not exist, provided the MySQL account has the necessary permissions.

If your MySQL password is not empty, configure the `DB_PASS` environment variable or update the database credentials locally.

**Important:** Do not upload your real database password to GitHub.

**Option B: Use H2 Database**

You can run the application without installing MySQL by using the H2 in-memory database.

Run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

The H2 database is configured for development and testing. Because it is an in-memory database, its data is normally lost when the application stops.

### Step 3: Build the Project

Run the following command from the project directory:

```bash
mvn clean install
```

This command compiles the application, resolves dependencies, and packages the project.

### Step 4: Run the Application

For MySQL:

```bash
mvn spring-boot:run
```

For H2:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

Wait until Spring Boot starts successfully.

### Step 5: Open the Website

Open your browser and visit:

**http://localhost:8080**

The Bookwise homepage should appear.

## 🔌 REST API Endpoints

The backend provides REST endpoints for managing books, readers, ratings, and recommendations.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/books` | Retrieve all books |
| GET | `/api/books?q=fiction` | Search books by title, author, or genre |
| POST | `/api/books` | Add a new book |
| GET | `/api/users` | Retrieve all readers |
| POST | `/api/users` | Register a new reader |
| POST | `/api/ratings` | Submit or update a book rating |
| GET | `/api/recommendations/{userId}` | Retrieve book recommendations |
| GET | `/api/db-status` | Check database connectivity |

### Example: Add a New Reader

**Endpoint:**

```http
POST /api/users
Content-Type: application/json
```

**Request Body:**

```json
{
  "username": "reader1"
}
```

### Example: Rate a Book

**Endpoint:**

```http
POST /api/ratings
Content-Type: application/json
```

**Request Body:**

```json
{
  "userId": 1,
  "bookId": 2,
  "score": 5
}
```

The rating score must be between 1 and 5.

### Example: Get Recommendations

```http
GET /api/recommendations/1
```

Replace `1` with the ID of the reader whose recommendations you want to retrieve.

## 🧠 Recommendation Algorithm

Bookwise uses a genre-based recommendation approach implemented with JDBC.

The recommendation process works as follows:

1. Retrieve the genres of books rated 4 or 5 stars by the selected reader.
2. Find other books belonging to those genres.
3. Exclude books the reader has already rated.
4. Calculate the average rating and number of ratings for the remaining books.
5. Sort the results by average rating and rating count.
6. Return up to 10 recommended books.

If no personalized recommendations are available, the application falls back to recommending highly rated books that the reader has not rated.

**Example:**

If a reader gives a science-fiction book 5 stars, Bookwise can recommend other science-fiction books that the reader has not rated.

This approach helps readers discover books related to their interests.

## 🗄️ Database Design

The application uses three main database tables.

### 1. Users Table

Stores reader information.

| Column | Description |
|---|---|
| id | Unique reader ID |
| username | Reader's username |

### 2. Books Table

Stores information about books.

| Column | Description |
|---|---|
| id | Unique book ID |
| title | Name of the book |
| author | Book author |
| genre | Book category |
| published_year | Year of publication |
| description | Short description of the book |

### 3. Ratings Table

Stores the ratings given by readers.

| Column | Description |
|---|---|
| id | Unique rating ID |
| user_id | ID of the reader |
| book_id | ID of the book |
| score | Rating from 1 to 5 |

The ratings table connects readers with books through their respective IDs. Each reader can have one rating per book.

Hibernate manages the database schema using the application's JPA configuration. The `schema.sql` file is also provided as a database schema reference.

## 🧪 Testing the Application

Follow these steps to test the main features:

1. Start the application.
2. Open the homepage in your browser.
3. Check whether the database connection status is displayed.
4. Browse the available books.
5. Search for a book by its title, author, or genre.
6. Select a reader from the reader dropdown.
7. Open a book and submit a rating.
8. Refresh the recommendations to see whether the results change.
9. Add a new reader and verify that the reader appears in the application.
10. Add a new book and check whether it appears in the book collection.

You can also test the REST API endpoints using a tool such as Postman.

## 🎯 Project Objectives

- To develop a full-stack book recommendation web application.
- To understand client-server communication.
- To implement REST APIs using Spring Boot.
- To perform database operations using JDBC and Hibernate.
- To store and retrieve books, readers, and ratings.
- To implement a basic personalized recommendation algorithm.
- To demonstrate database connectivity and SQL query execution.
- To create a user-friendly and responsive web interface.

## 🔮 Future Enhancements

The application could be extended with the following features:

- User authentication and secure login.
- Password encryption and role-based access.
- Collaborative filtering recommendations.
- More advanced machine-learning recommendation algorithms.
- Book cover images and external book information APIs.
- Pagination and advanced search filters.
- User reviews and comments.
- Reading lists and favorite books.
- Deployment to a cloud platform.

These are possible future improvements and are not necessarily included in the current implementation.

## 👩‍💻 Author

**Project Name:** Bookwise – Book Recommendation System

**Project Type:** Full-Stack Web Application

**Domain:** Web Development and Database Management

**Technologies:** Java, Spring Boot, JDBC, Hibernate, MySQL, HTML, CSS, JavaScript

## 📄 License

This project was developed for educational and academic purposes.

You may adapt this section if your project uses a specific open-source license.
