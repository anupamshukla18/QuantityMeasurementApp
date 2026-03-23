# UC15 – N-Tier Architecture Refactoring (Quantity Measurement Application)

## Overview
UC15 refactors the **Quantity Measurement Application** from a monolithic design into a professional **N-Tier architecture**.  
The application is organized into multiple layers to improve **separation of concerns, maintainability, scalability, and testability.**

---

## Architecture

The application follows a **layered architecture**:

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
Entity / Model Layer
```

---

## Layers Description

### 1. Application Layer
Entry point of the application.

**Class**
- `QuantityMeasurementApp`

**Responsibilities**
- Initialize components
- Start the application
- Call controller methods

---

### 2. Controller Layer
Handles user requests and delegates operations to the service layer.

**Class**
- `QuantityMeasurementController`

**Responsibilities**
- Accept user input
- Call service methods
- Display results

---

### 3. Service Layer
Contains the core **business logic** for quantity operations.

**Interface**
- `IQuantityMeasurementService`

**Implementation**
- `QuantityMeasurementServiceImpl`

**Supported Operations**
- Compare quantities
- Add quantities
- Subtract quantities
- Divide quantities

---

### 4. Repository Layer
Responsible for storing **measurement history**.

**Interface**
- `IQuantityMeasurementRepository`

**Implementation**
- `QuantityMeasurementCacheRepository`

**Special Implementation**
- Uses **Singleton Pattern** to maintain a single repository instance.

---

### 5. Entity / Model Layer
Classes used for **data representation**:

- `QuantityDTO`
- `QuantityModel`
- `QuantityMeasurementEntity`

**Purpose**

| Class | Description |
|------|-------------|
| DTO | Used for transferring data between layers |
| Model | Internal service representation |
| Entity | Stores measurement records |

---

## Design Principles Used

- Separation of Concerns
- SOLID Principles
- Dependency Injection

---

## Design Patterns Used

- Singleton Pattern
- Factory Pattern
- Facade Pattern

---

## Testing

JUnit test cases are implemented to verify:

- Service operations
- Controller flow
- Repository behavior
- Edge cases
