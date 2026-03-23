# UC17 — Spring Boot REST API for Quantity Measurement Application

## Table of Contents
1. [Overview](#overview)
2. [What Changed from UC16](#what-changed-from-uc16)
3. [Project Structure](#project-structure)
4. [Technology Stack](#technology-stack)
5. [Architecture](#architecture)
6. [File by File Explanation](#file-by-file-explanation)
7. [REST API Endpoints](#rest-api-endpoints)
8. [Database](#database)
9. [Security](#security)
10. [Exception Handling](#exception-handling)
11. [How to Run](#how-to-run)
12. [How to Test](#how-to-test)
13. [Key Concepts Learned](#key-concepts-learned)
14. [CURL Examples](#curl-examples)

---

## Overview

UC17 transforms the standalone Quantity Measurement Application (UC16) into a **Spring Boot REST Service**.

| Feature | UC16 | UC17 |
|---------|------|------|
| Framework | Plain Java | Spring Boot 3.1.0 |
| Database Access | JDBC (manual) | Spring Data JPA (automatic) |
| API Exposure | None | REST Endpoints |
| Configuration | Manual properties | Spring auto-configuration |
| Dependency Injection | Manual | Spring `@Autowired` |
| Testing | JUnit | MockMvc + SpringBootTest |
| Documentation | None | Swagger / OpenAPI |
| Security | None | Spring Security |
| Server | None | Embedded Tomcat (port 8080) |

---

## What Changed from UC16

### 🆕 NEW Files
| File | Purpose |
|------|---------|
| `QuantityMeasurementApplication.java` | Spring Boot main class |
| `SecurityConfig.java` | Spring Security configuration |
| `OperationType.java` | Enum for ADD, SUBTRACT, DIVIDE, MULTIPLY, COMPARE, CONVERT |
| `QuantityInputDTO.java` | Input DTO for REST endpoints |
| `QuantityMeasurementDTO.java` | Output DTO with static factory methods |
| `QuantityMeasurementRepository.java` | Spring Data JPA repository |
| `GlobalExceptionHandler.java` | Centralized exception handling |
| `QuantityMeasurementControllerTest.java` | MockMvc controller tests |
| `QuantityMeasurementApplicationTests.java` | Integration tests |

### 🔄 MODIFIED Files
| File | What Changed |
|------|-------------|
| `pom.xml` | Added Spring Boot parent + all dependencies |
| `QuantityMeasurementEntity.java` | Moved to `model/` + added JPA annotations |
| `QuantityDTO.java` | Added validation annotations |
| `IQuantityMeasurementService.java` | Updated method signatures |
| `QuantityMeasurementServiceImpl.java` | Added `@Service`, updated to use JPA repository |
| `QuantityMeasurementController.java` | Full REST controller with Swagger |
| `application.properties` | Added `spring.application.name` |

### 🗑️ DUMPED Files (moved to dump/ folder)
| File | Reason |
|------|--------|
| `QuantityMeasurementApp.java` | Replaced by Spring Boot Application class |
| `IQuantityMeasurementRepository.java` | Replaced by Spring Data JPA |
| `QuantityMeasurementCacheRepository.java` | Replaced by Spring Data JPA |
| `QuantityMeasurementDatabaseRepository.java` | Replaced by Spring Data JPA |
| `ApplicationConfig.java` | Spring Boot handles config automatically |
| `ConnectionPool.java` | HikariCP handles connections automatically |

---

## Project Structure

```
quantity-measurement-app/
│
├── pom.xml                                          Spring Boot dependencies
│
├── src/main/java/com/apps/
│   ├── app/
│   │   └── QuantityMeasurementApplication.java      Spring Boot main class
│   │
│   ├── config/
│   │   └── SecurityConfig.java                      Security configuration
│   │
│   ├── controller/
│   │   └── QuantityMeasurementController.java        REST endpoints
│   │
│   ├── core/                                         NO CHANGE - business logic
│   │   ├── IMeasurable.java
│   │   ├── LengthUnit.java
│   │   ├── Quantity.java
│   │   ├── SupportsArithmetic.java
│   │   ├── TemperatureUnit.java
│   │   ├── VolumeUnit.java
│   │   └── WeightUnit.java
│   │
│   ├── dto/
│   │   ├── OperationType.java                        NEW - operation enum
│   │   ├── QuantityDTO.java                          MODIFIED - added validation
│   │   ├── QuantityInputDTO.java                     NEW - REST input wrapper
│   │   └── QuantityMeasurementDTO.java               NEW - REST output with factory methods
│   │
│   ├── exception/
│   │   ├── DatabaseException.java                    NO CHANGE
│   │   ├── GlobalExceptionHandler.java               NEW - centralized error handling
│   │   └── QuantityMeasurementException.java         NO CHANGE
│   │
│   ├── model/
│   │   ├── QuantityMeasurementEntity.java            MODIFIED - JPA annotations
│   │   └── QuantityModel.java                        NO CHANGE
│   │
│   ├── repository/
│   │   └── QuantityMeasurementRepository.java        NEW - Spring Data JPA
│   │
│   └── service/
│       ├── IQuantityMeasurementService.java          MODIFIED - new signatures
│       └── QuantityMeasurementServiceImpl.java       MODIFIED - Spring @Service
│
├── src/main/resources/
│   ├── application.properties                        spring.application.name only
│   ├── application-dev.properties                    H2 database + DEBUG logging
│   └── application-prod.properties                   MySQL + WARN logging
│
├── src/test/java/com/apps/
│   ├── app/
│   │   └── QuantityMeasurementApplicationTests.java  Integration tests
│   └── controller/
│       └── QuantityMeasurementControllerTest.java    MockMvc unit tests
│
└── dump/                                             UC16 deprecated files
    ├── QuantityMeasurementApp.java
    ├── IQuantityMeasurementRepository.java
    ├── QuantityMeasurementCacheRepository.java
    ├── QuantityMeasurementDatabaseRepository.java
    ├── ApplicationConfig.java
    └── ConnectionPool.java
```

---

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Spring Boot | 3.1.0 | Application framework |
| Spring Web | 3.1.0 | REST endpoints + Embedded Tomcat |
| Spring Data JPA | 3.1.0 | ORM - replaces JDBC |
| Spring Security | 3.1.0 | Authentication/Authorization |
| Spring Actuator | 3.1.0 | Health checks + Metrics |
| H2 Database | Runtime | In-memory DB for development |
| MySQL | Runtime | Production database |
| Hibernate | Auto | JPA implementation |
| HikariCP | Auto | Connection pooling |
| SpringDoc OpenAPI | 2.1.0 | Swagger UI documentation |
| Lombok | Latest | Reduce boilerplate code |
| JUnit 5 | Auto | Unit + Integration testing |
| Mockito | Auto | Mocking in tests |

---

## Architecture

```
CLIENT (Browser/Postman/CURL)
        │
        ▼
┌─────────────────────────────┐
│   REST Controller           │  @RestController
│   QuantityMeasurementController│  Handles HTTP requests
│   /api/v1/quantities/**     │  Returns JSON responses
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│   Service Layer             │  @Service
│   QuantityMeasurementServiceImpl│  Business logic
│                             │  Converts DTOs ↔ Quantity objects
└─────────────┬───────────────┘
              │
      ┌───────┴───────┐
      ▼               ▼
┌──────────┐    ┌──────────────┐
│  Core    │    │  Repository  │  @Repository
│  Layer   │    │  Spring Data │  Extends JpaRepository
│ Quantity │    │  JPA         │  Auto CRUD + Custom queries
│ Units    │    └──────┬───────┘
└──────────┘           │
                       ▼
              ┌─────────────────┐
              │   H2 Database   │  Development
              │   (in-memory)   │  jdbc:h2:mem:quantitymeasurementdb
              └─────────────────┘
```

---

## File by File Explanation

### 1. `QuantityMeasurementApplication.java`
- Entry point of Spring Boot application
- `@SpringBootApplication` — enables auto-configuration, component scanning
- `scanBasePackages = "com.apps"` — scans all packages

### 2. `SecurityConfig.java`
- Disables CSRF for REST API
- Permits all requests for development
- Allows H2 console iframe rendering
- Foundation for future JWT/OAuth2 security

### 3. `QuantityMeasurementEntity.java` (moved to `model/`)
- `@Entity` — marks as JPA entity mapped to database table
- `@Table` — defines table name + 3 indexes for performance
- `@Id` + `@GeneratedValue(IDENTITY)` — auto-increment primary key
- `@Column(name=...)` — explicit column name mappings
- `@PrePersist` + `@PreUpdate` — auto timestamps on save/update

### 4. `QuantityMeasurementRepository.java`
- Extends `JpaRepository<QuantityMeasurementEntity, Long>`
- Provides CRUD for free — no SQL needed
- `findByOperation()` — auto-generated from method name
- `findByThisMeasurementType()` — auto-generated from method name
- `findByCreatedAtAfter()` — auto-generated from method name
- `@Query` — custom JPQL for complex queries
- `countByOperationAndIsErrorFalse()` — count successful operations
- `findByIsErrorTrue()` — find all errored records

### 5. `QuantityDTO.java`
- `@NotNull` — value cannot be null
- `@NotEmpty` — unit and measurementType cannot be empty
- `@Pattern` — measurementType must match valid values
- `@AssertTrue` — custom validation: unit must match measurementType

### 6. `QuantityMeasurementDTO.java`
- Output DTO for all REST responses
- `fromEntity()` — converts Entity → DTO
- `toEntity()` — converts DTO → Entity
- `fromEntityList()` — converts List<Entity> → List<DTO> using Stream API
- `toEntityList()` — converts List<DTO> → List<Entity> using Stream API

### 7. `QuantityInputDTO.java`
- Input wrapper for all REST endpoints
- Contains `thisQuantityDTO` + `thatQuantityDTO`
- `@Valid` — cascades validation to nested DTOs
- Optional `targetUnit` for convert/add/subtract

### 8. `IQuantityMeasurementService.java`
- All methods accept `QuantityInputDTO` as input
- All methods return `QuantityMeasurementDTO` as output
- 4 new history/reporting methods added

### 9. `QuantityMeasurementServiceImpl.java`
- `@Service` — Spring registers as service bean
- `@Autowired` — field injection for repository
- `convertDtoToModel()` — converts DTO to Quantity object
- Error handling saves errors to DB for tracking
- No `@Transactional` — errors saved even when exceptions occur

### 10. `QuantityMeasurementController.java`
- `@RestController` — handles HTTP + returns JSON
- `@RequestMapping("/api/v1/quantities")` — base URL
- `@Tag` — Swagger grouping
- `@Operation` — Swagger description per endpoint
- `@Valid` — triggers input validation
- `ResponseEntity` — proper HTTP status codes

### 11. `GlobalExceptionHandler.java`
- `@ControllerAdvice` — intercepts all controller exceptions
- `handleMethodArgumentNotValidException()` — validation errors → 400
- `handleQuantityException()` — business errors → 400
- `handleGlobalException()` — all other errors → 500
- Consistent error response: timestamp, status, error, message, path

---

## REST API Endpoints

### Base URL: `http://localhost:8080/api/v1/quantities`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/compare` | Compare two quantities |
| POST | `/convert` | Convert quantity to another unit |
| POST | `/add` | Add two quantities |
| POST | `/subtract` | Subtract two quantities |
| POST | `/divide` | Divide two quantities |
| GET | `/history/operation/{operation}` | Get history by operation type |
| GET | `/history/type/{measurementType}` | Get history by measurement type |
| GET | `/count/{operation}` | Get count of successful operations |
| GET | `/history/errored` | Get all errored measurements |

### Request Body (POST endpoints)
```json
{
  "thisQuantityDTO": {
    "value": 1.0,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantityDTO": {
    "value": 12.0,
    "unit": "INCHES",
    "measurementType": "LengthUnit"
  }
}
```

### Response Body
```json
{
  "thisValue": 1.0,
  "thisUnit": "FEET",
  "thisMeasurementType": "LengthUnit",
  "thatValue": 12.0,
  "thatUnit": "INCHES",
  "thatMeasurementType": "LengthUnit",
  "operation": "compare",
  "resultString": "true",
  "resultValue": 0.0,
  "resultUnit": null,
  "resultMeasurementType": null,
  "errorMessage": null,
  "error": false
}
```

### Valid Units per Measurement Type
| Measurement Type | Valid Units |
|-----------------|-------------|
| LengthUnit | FEET, INCHES, YARDS, CENTIMETERS |
| WeightUnit | MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE |
| VolumeUnit | LITRE, MILLILITRE, GALLON |
| TemperatureUnit | CELSIUS, FAHRENHEIT, KELVIN |

---

## Database

### Development — H2 In-Memory
```properties
URL:      jdbc:h2:mem:quantitymeasurementdb
Username: sa
Password: (blank)
Console:  http://localhost:8080/h2-console
```

### Production — MySQL
```properties
URL:      jdbc:mysql://localhost:3306/quantitymeasurementdb
Username: root
Password: your_password
```

### Table: `QUANTITY_MEASUREMENT_ENTITY`
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-increment primary key |
| this_value | DOUBLE | First quantity value |
| this_unit | VARCHAR | First quantity unit |
| this_measurement_type | VARCHAR | First quantity type |
| that_value | DOUBLE | Second quantity value |
| that_unit | VARCHAR | Second quantity unit |
| that_measurement_type | VARCHAR | Second quantity type |
| operation | VARCHAR | Operation performed |
| result_string | VARCHAR | String result (compare) |
| result_value | DOUBLE | Numeric result |
| result_unit | VARCHAR | Result unit |
| result_measurement_type | VARCHAR | Result type |
| error_message | VARCHAR | Error details if any |
| is_error | BOOLEAN | Whether operation errored |
| created_at | TIMESTAMP | Auto-set on insert |
| updated_at | TIMESTAMP | Auto-set on update |

---

## Security

- Spring Security included but **permissive** for development
- All endpoints permitted without authentication
- CSRF disabled for REST API
- H2 console frame rendering enabled
- Foundation ready for JWT/OAuth2 in future

---

## Exception Handling

### Error Response Format
```json
{
  "timestamp": "2026-03-23T10:30:00",
  "status": 400,
  "error": "Quantity Measurement Error",
  "message": "Unit must be valid for the specified measurement type",
  "path": "/api/v1/quantities/compare"
}
```

### HTTP Status Codes
| Scenario | Status Code |
|----------|------------|
| Success | 200 OK |
| Validation error (invalid unit) | 400 Bad Request |
| Business logic error | 400 Bad Request |
| Server error | 500 Internal Server Error |

---


### Test Types
| Test Class | Type | Annotation | What it tests |
|------------|------|------------|---------------|
| `QuantityMeasurementControllerTest` | Unit | `@WebMvcTest` | Controller layer only, mocks service |
| `QuantityMeasurementApplicationTests` | Integration | `@SpringBootTest` | Full application stack |

---

## Key Concepts Learned

### 1. Spring Boot Auto-Configuration
- `@SpringBootApplication` enables automatic bean registration
- No XML configuration needed
- Embedded Tomcat — no external server required

### 2. Spring Data JPA
- Extends `JpaRepository` → free CRUD operations
- Method name conventions → auto-generated SQL
- `@Query` for custom JPQL queries
- Eliminates all JDBC boilerplate

### 3. REST Controllers
- `@RestController` = `@Controller` + `@ResponseBody`
- `@RequestMapping` defines base URL
- `@PostMapping`, `@GetMapping` map HTTP methods
- `ResponseEntity` controls HTTP status codes

### 4. Dependency Injection
- `@Autowired` injects beans automatically
- `@Service`, `@Repository`, `@Controller` register beans
- No manual object creation needed

### 5. Validation
- `@NotNull`, `@NotEmpty`, `@Pattern` — field-level validation
- `@AssertTrue` — custom validation logic
- `@Valid` — triggers validation cascade
- `MethodArgumentNotValidException` — caught by GlobalExceptionHandler

### 6. Exception Handling
- `@ControllerAdvice` — centralizes exception handling
- `@ExceptionHandler` — handles specific exception types
- Consistent error response format across all endpoints

### 7. Spring Security
- `SecurityFilterChain` — modern Spring Boot 3.x approach
- CSRF disabled for stateless REST APIs
- Permissive config for development

### 8. Testing
- `@WebMvcTest` — loads only web layer (fast)
- `@MockBean` — mocks service layer
- `MockMvc` — performs HTTP requests in tests
- `@SpringBootTest` — loads full application context
- `TestRestTemplate` — real HTTP calls in integration tests

### 9. API Documentation
- `@Tag` — groups endpoints in Swagger
- `@Operation` — describes each endpoint
- Auto-generated from code annotations

### 10. Profiles
- `application-dev.properties` — H2, DEBUG logging
- `application-prod.properties` — MySQL, WARN logging
- Activated via `spring.profiles.active=dev`

---

## CURL Examples

```bash
# Compare
curl -X POST http://localhost:8080/api/v1/quantities/compare \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":12.0,"unit":"INCHES","measurementType":"LengthUnit"}}'

# Convert
curl -X POST http://localhost:8080/api/v1/quantities/convert \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":0.0,"unit":"INCHES","measurementType":"LengthUnit"}}'

# Add
curl -X POST http://localhost:8080/api/v1/quantities/add \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":12.0,"unit":"INCHES","measurementType":"LengthUnit"}}'

# Subtract
curl -X POST http://localhost:8080/api/v1/quantities/subtract \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":6.0,"unit":"INCHES","measurementType":"LengthUnit"}}'

# Divide
curl -X POST http://localhost:8080/api/v1/quantities/divide \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":12.0,"unit":"INCHES","measurementType":"LengthUnit"}}'

# History by operation
curl http://localhost:8080/api/v1/quantities/history/operation/compare

# History by type
curl http://localhost:8080/api/v1/quantities/history/type/LengthUnit

# Count
curl http://localhost:8080/api/v1/quantities/count/compare

# Error history
curl http://localhost:8080/api/v1/quantities/history/errored

# Error scenario - incompatible types
curl -X POST http://localhost:8080/api/v1/quantities/add \
  -H "Content-Type: application/json" \
  -d '{"thisQuantityDTO":{"value":1.0,"unit":"FEET","measurementType":"LengthUnit"},"thatQuantityDTO":{"value":1.0,"unit":"KILOGRAM","measurementType":"WeightUnit"}}'
```

---

*UC17 Complete — Spring Boot REST API for Quantity Measurement Application*
