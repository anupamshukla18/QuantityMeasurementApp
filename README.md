## UC16 – Database Integration with JDBC for Quantity Measurement Persistence

### Overview

UC16 enhances the **Quantity Measurement Application** by introducing **persistent storage using JDBC**.
In the previous use case (UC15), the application followed an **N-Tier Architecture** with an in-memory cache repository.
UC16 extends this design by implementing a **database repository layer** that stores measurement operations in a relational database.

The system now supports **long-term persistence**, enabling storage, retrieval, and analysis of measurement operations.

---

### Architecture

The application continues to follow the **layered N-Tier architecture** introduced in UC15.

```
Application Layer
        |
        v
Controller Layer
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
Database (H2)
```

---

### Layers Description

#### Application Layer

Entry point of the application.

Class:

```
QuantityMeasurementApp
```

Responsibilities:

* Initialize repository implementation
* Configure database settings
* Start application execution
* Display measurement history

---

#### Controller Layer

Handles user requests and delegates them to the service layer.

Class:

```
QuantityMeasurementController
```

Responsibilities:

* Accept input quantities
* Call service methods
* Display results
* Handle user interaction logic

---

#### Service Layer

Contains the core business logic for quantity operations.

Interface:

```
IQuantityMeasurementService
```

Implementation:

```
QuantityMeasurementServiceImpl
```

Supported operations:

* Compare quantities
* Convert units
* Add quantities
* Subtract quantities
* Divide quantities

Responsibilities:

* Perform business logic
* Validate measurement categories
* Convert units before operations
* Persist results through repository layer

---

#### Repository Layer

Responsible for storing and retrieving measurement data.

Interface:

```
IQuantityMeasurementRepository
```

Implementations:

```
QuantityMeasurementCacheRepository
QuantityMeasurementDatabaseRepository
```

Repository Types:

| Repository          | Description                      |
| ------------------- | -------------------------------- |
| Cache Repository    | In-memory storage used in UC15   |
| Database Repository | JDBC implementation used in UC16 |

Responsibilities:

* Store measurement operations
* Retrieve measurement history
* Query operations
* Delete stored records

---

### Database Integration

UC16 introduces **JDBC-based persistence** using the **H2 database**.

Database Configuration:

```
jdbc:h2:./quantitydb
```

Database Table:

```
QUANTITY_MEASUREMENT_ENTITY
```

Table Structure:

| Column        | Description                          |
| ------------- | ------------------------------------ |
| id            | Primary key                          |
| operation     | Operation type (ADD, SUBTRACT, etc.) |
| operand1      | First quantity                       |
| operand2      | Second quantity                      |
| result        | Operation result                     |
| error_message | Error information                    |
| created_at    | Timestamp of operation               |

---

### Database Utilities

#### ApplicationConfig

Loads configuration from `application.properties`.

Responsibilities:

* Load database URL
* Load username and password
* Load repository configuration

---

#### ConnectionPool

Manages database connections.

Responsibilities:

* Maintain connection pool
* Reuse database connections
* Improve performance
* Prevent connection overhead

---

### Exception Handling

New exception introduced:

```
DatabaseException
```

Purpose:

* Handle JDBC errors
* Provide meaningful error messages
* Prevent application crashes

---

### Persistence Flow

Example Flow for Addition Operation:

```
Controller receives request
        |
        v
Service performs validation and conversion
        |
        v
Repository saves operation in database
        |
        v
Database stores measurement record
```

---

### Technologies Used

| Technology      | Purpose                         |
| --------------- | ------------------------------- |
| Java            | Core application logic          |
| Maven           | Build and dependency management |
| JDBC            | Database connectivity           |
| H2 Database     | Lightweight embedded database   |
| JUnit 5         | Unit testing                    |
| Mockito         | Mocking for tests               |
| SLF4J + Logback | Logging framework               |

---

### Key Features Introduced in UC16

* JDBC-based database persistence
* H2 embedded database integration
* Connection pooling for database performance
* Configurable repository implementation
* SQL-based data storage
* Secure parameterized queries
* Improved logging using SLF4J
* Extended unit and integration testing
* Maven dependency management

---

### Advantages of UC16 Implementation

| Improvement        | Benefit                            |
| ------------------ | ---------------------------------- |
| Persistent Storage | Data survives application restarts |
| JDBC Integration   | Standard database connectivity     |
| Connection Pooling | Efficient resource usage           |
| Query Support      | Retrieve measurement history       |
| Database Schema    | Structured data storage            |
| Scalability        | Supports larger datasets           |

---

### Testing

JUnit test cases verify:

* Database connection creation
* Repository CRUD operations
* SQL query execution
* Connection pool functionality
* Data persistence
* Service layer integration
* Controller functionality

All **UC1–UC15 test cases continue to pass**, ensuring backward compatibility.

---

### Postconditions

After implementing UC16:

* The application supports **database persistence**
* Measurement operations are stored in a **relational database**
* The repository layer supports **both cache and database storage**
* Database queries can retrieve stored measurements
* Connection pooling improves performance
* The system is ready for **future REST API integration**
* The project follows a **professional Maven project structure**

---

### Summary

UC16 transforms the Quantity Measurement Application from an **in-memory system** into a **database-backed application** using JDBC.

This enhancement improves:

* Persistence
* Scalability
* Maintainability
* Data analysis capabilities

The system now supports long-term storage of measurement operations and prepares the application for **future enterprise-level features such as REST APIs and distributed deployment**.
