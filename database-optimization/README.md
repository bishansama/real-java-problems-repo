# Database Optimization - H2 CRUD Demo

This is a simple Spring Boot 3.5 and Java 21 application using an in-memory H2 database to demonstrate CRUD operations.

## Prerequisites
- Java 21
- Maven

## Run
```bash
cd database-optimization
mvn spring-boot:run
```
App listens on `http://localhost:8081`.

H2 console: `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, username: `sa`, empty password)

## API
- `GET /api/customers` — list all
- `GET /api/customers/{id}` — get by id
- `POST /api/customers` — create
  ```json
  { "name": "Alice", "email": "alice@example.com" }
  ```
- `PUT /api/customers/{id}` — update
- `DELETE /api/customers/{id}` — delete

## Notes
- Schema is auto-created via JPA (`ddl-auto=update`).
- Validation is enabled on name and email.
- Email is unique.