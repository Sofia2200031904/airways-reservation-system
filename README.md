# Airways Reservation System

A Spring Boot REST API for an airline reservation workflow. It includes flight search and management, airport and passenger data, booking operations, and account registration and login.

## Stack

- Java 17 and Spring Boot 3.1.5
- Spring Web, Spring Data JPA, Spring Security, and Bean Validation
- MySQL (with H2 available as a runtime dependency)
- Maven

## Requirements

- JDK 17
- Maven 3.8+
- MySQL 8+ (or a compatible MySQL server)

## Run locally

1. Create a local database, or allow the configured JDBC URL to create `airways_db` for your MySQL user.
2. Set database and JWT values in your shell. For PowerShell:

   ```powershell
   $env:DB_URL = "jdbc:mysql://localhost:3306/airways_db?createDatabaseIfNotExist=true"
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "your-local-password"
   $env:JWT_SECRET = "use-a-long-random-secret-value"
   ```

3. Start the API:

   ```bash
   mvn spring-boot:run
   ```

The server listens on `http://localhost:8080` by default. Database and server settings can be overridden with `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION`, and `SERVER_PORT`.

## API overview

All API routes use the `/api` prefix.

| Area | Routes |
| --- | --- |
| Authentication | `POST /api/auth/register`, `POST /api/auth/login` |
| Airports | `GET /api/airports`, `GET /api/airports/{id}`, `GET /api/airports/code/{code}`, `GET /api/airports/search`, `POST /api/airports`, `GET /api/airports/countries`, `GET /api/airports/countries/{countryId}/cities` |
| Flights | `GET /api/flights`, `GET /api/flights/{id}`, `GET /api/flights/number/{flightNumber}`, `POST /api/flights/search`, `POST /api/flights`, `PUT /api/flights/{id}`, `DELETE /api/flights/{id}` |
| Passengers | `GET /api/passengers`, `GET /api/passengers/{id}`, `GET /api/passengers/email/{email}`, `POST /api/passengers`, `PUT /api/passengers/{id}`, `DELETE /api/passengers/{id}` |
| Bookings | `POST /api/bookings`, `GET /api/bookings/{reference}`, `GET /api/bookings`, `DELETE /api/bookings/{reference}` |

Request and response models are defined in `src/main/java/com/airways/dto`. See the controller classes for accepted payload fields and response details.

## Project layout

```text
src/main/java/com/airways/
  config/       Security and CORS configuration
  controller/   REST endpoints
  dto/          API request and response models
  model/        JPA entities
  repository/   Spring Data repositories
  service/      Application logic
src/main/resources/
  application.properties
  schema.sql
```

## Notes

- CORS is configured for a local frontend at `http://localhost:3000`; update `CorsConfig` and controller annotations when using another origin.
- The current security configuration permits `/api/**`. Review and tighten authorization before deploying a public instance.
- `schema.sql` contains example database setup and seed data. Review it before applying to a non-disposable database.
- No production credentials should be committed. Provide secrets through environment variables or a deployment secret manager.
