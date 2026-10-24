# Book Recommendation System (Full Stack: JDBC + Hibernate + MySQL)

**Architecture:** Browser (HTML/JS) -> Spring Boot REST API -> Hibernate (JPA) for CRUD + plain JDBC for recommendation SQL -> MySQL.

## Run
1. Install JDK 17+, Maven, MySQL (or skip MySQL and use the H2 profile).
2. Set credentials in `src/main/resources/application.properties` (or env vars DB_URL, DB_USER, DB_PASS).
3. `mvn spring-boot:run`   (no MySQL: `mvn spring-boot:run -Dspring-boot.run.profiles=h2`)
4. Open http://localhost:8080

## Demo script
- Top panel shows live JDBC connection metadata (proves client-server-database connectivity).
- Pick user `alice`: recommendations show unrated Sci-Fi books (she rated Dune 5, Neuromancer 4).
- Rate a Romance book 5 for alice, refresh: Romance titles appear.
- Add a book / new user: rows persist in MySQL (`SELECT * FROM books;`).

## API
GET /api/books?q= | POST /api/books | GET,POST /api/users | POST /api/ratings {userId,bookId,score}
GET /api/recommendations/{userId} (JDBC) | GET /api/db-status (JDBC)
