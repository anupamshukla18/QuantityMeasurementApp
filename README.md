# Quantity Measurement App

🧾 Project Overview

The Quantity Measurement App is a Test-Driven Development (TDD) based project designed to demonstrate how to build scalable, maintainable software by starting simple and progressively adding complexity.

The application focuses on comparing and converting length measurements across different units while strictly following:

Test Driven Development (TDD)

Incremental Development

Clean Code Principles

DRY (Don't Repeat Yourself)

Proper Git Workflow (feature branches + PR)

The project is built step-by-step through Use Cases (UCs).
Each UC introduces a small feature and refactors the design to keep the code maintainable and extensible.

🧪 Development Methodology

This project follows the TDD Cycle:

🔴 Write failing test

🟢 Write minimal code to pass

🔵 Refactor without breaking tests

This ensures:

Safety

Maintainability

Scalability

🌳 Git Workflow Used

We followed a professional branching strategy:

main → stable production code

dev → integration branch

feature/UCx-* → individual feature branches

Each UC was:

Developed in feature branch

Tested locally

Pushed & PR created

Merged into dev

📚 USE CASE IMPLEMENTATION
## 🟢 UC1 — Feet Equality
🎯 Goal

Compare two Feet measurements for equality.

🧪 Tests Written

We validated the equals contract:

Same value → equal

Different value → not equal

Null comparison → false

Different object type → false

Same reference → true

💻 Implementation

Created Feet class with:

value field

equals() method

🧠 Learning Outcome

Understanding equality contract

First step of TDD

## 🟢 UC2 — Inches Equality
🎯 Goal

Support Inches unit in addition to Feet.

🧪 Tests Written

Repeated same equality tests for:

Inches = Inches

💻 Implementation

Created Inches class similar to Feet.

⚠️ Problem Observed

Huge code duplication:

Feet and Inches had identical logic.

🧠 Learning Outcome

Recognized need for refactoring (DRY violation).

Adding features without modifying logic

## 🔵 UC3 — Refactor to Generic Length Class
🎯 Goal

Remove duplication by introducing a generic measurement model.

🛠 Refactoring Done

Removed:

❌ Feet class

❌ Inches class

Introduced:

✅ Length class

✅ LengthUnit enum

🧠 Core Design Change

Instead of multiple classes:

Feet
Inches

We created one generic model:

Length(value, LengthUnit)
📐 Base Unit Concept

All units converted internally to INCHES (base unit).

FEET  → 12 inches
INCHES → 1 inch

Added method:

convertToBaseUnit()
🧪 Tests Covered

✔ Feet = Feet
✔ Inches = Inches
✔ 1 Foot = 12 Inches
✔ Symmetry
✔ Transitive equality
✔ equals contract validation

🧠 Learning Outcome

Refactoring safely using tests

Generic design

Domain modeling

DRY principle

## 🟣 UC4 — Add New Units (Extensibility Proof)
🎯 Goal

Prove that the design is scalable by adding new units without modifying core logic.

➕ New Units Added

YARDS

CENTIMETERS

Updated enum only — no logic changes.

📐 Conversion Factors
Unit	Inches
1 Foot	12
1 Yard	36
1 Inch	1
1 cm	0.393701
🧪 Tests Added

✔ Yard = Yard
✔ Yard = Feet
✔ Yard = Inches
✔ Feet = Yard (symmetry)
✔ Inches = Yard (symmetry)
✔ Centimeter = Inches
✔ Centimeter ≠ Feet
✔ Transitive property

🧠 Learning Outcome

Extensible architecture

Open/Closed Principle

Adding features without modifying logic

## 🔵 UC5 — Unit Conversion
🎯 Goal

Add the ability to convert length from one unit to another.

Until UC4, the app could only compare units.
UC5 introduces an explicit conversion API.

⚙️ Features Added

Static conversion method

convert(value, fromUnit, toUnit)

Instance conversion method in Length class

length.convertTo(targetUnit)

Overloaded helper methods for easy usage.

🧪 Test Coverage

Feet ↔ Inches conversion

Yards ↔ Inches conversion

Centimeters ↔ Inches conversion

Zero & negative values

Round-trip conversion

Null & NaN validation

🧠 Learning Outcome

UC5 demonstrates:

Reusable design from previous UCs

Clean API design

Validation & edge-case handling

## UC6 – Addition of Two Length Units
Overview

UC6 extends the Quantity Measurement App by adding addition operations between two length measurements.
Users can now add values with same or different units and get the result in the unit of the first operand.

Example:

1 Foot + 12 Inches = 2 Feet

What was implemented

Added add() method in Length class

Supports addition across units:

Feet, Inches, Yards, Centimeters

Uses base unit normalization (inches) before addition

Returns a new immutable Length object

Maintains floating-point precision using rounding

Ensures input validation and error handling

Key Features

Same-unit addition (Feet + Feet)

Cross-unit addition (Feet + Inches, Yards + Feet, etc.)

Result returned in unit of first operand

Supports zero, negative, large and small values

Null and invalid inputs throw exceptions

Addition follows commutative property

Example Usage
Length l1 = new Length(1, Length.LengthUnit.FEET);
Length l2 = new Length(12, Length.LengthUnit.INCHES);

Length result = l1.add(l2);
System.out.println(result); // 2.00 FEET

## UC7 – Addition with Target Unit Specification
Overview

UC7 extends the length addition feature by allowing the caller to explicitly choose the unit of the result.
Instead of always returning the result in the unit of the first operand (UC6), the result can now be returned in any supported length unit.

What was implemented

Added overloaded add method to support target unit:

add(Length length1, Length length2, LengthUnit targetUnit)

Both lengths are:

Converted to base unit (inches)

Added together

Converted to the specified target unit

Returned as a new immutable Length object

Supported Units

FEET

INCHES

YARDS

CENTIMETERS

Key Features

Explicit control over result unit

Maintains immutability of objects

Reuses conversion logic from UC5

Maintains backward compatibility with UC6

Validates null units and invalid inputs

Example
Input	Target Unit	Result
1 ft + 12 in	FEET	2 ft
1 ft + 12 in	INCHES	24 in
1 ft + 12 in	YARDS	0.667 yd
Concepts Covered

Method Overloading

DRY Principle (shared conversion logic)

Explicit parameter design

Floating-point precision handling

Robust validation & exception handling

## 📘 UC8 – Refactoring Unit Enum to Standalone Class
🔹 Overview

UC8 refactors the architecture by extracting LengthUnit enum from the Length class into a standalone top-level enum.
This follows the Single Responsibility Principle (SRP) and makes the design scalable for future measurement types (Weight, Volume, Temperature).

🔹 What Changed in UC8

LengthUnit moved to its own class.

All conversion logic is now handled inside LengthUnit.

Length class now focuses only on:

equality

conversion delegation

addition operations

Circular dependency risk removed.

All UC1 → UC7 functionality works without any change.

🔹 New Capabilities

Units now handle:

convertToBaseUnit()

convertFromBaseUnit()

Cleaner architecture & better separation of concerns.

Easy to add new measurement categories in future.

🔹 Result

No breaking changes.

All previous tests pass.

## UC9- Addition of Weight Measurement
📌 Feature Added

UC9 extends the Quantity Measurement App by introducing a new measurement category: Weight.

The system now supports multiple independent measurement categories:

Length (existing UC1–UC8)

Weight (new in UC9)

⚖️ Supported Weight Units
Unit	Base Conversion
Kilogram (kg)	Base unit
Gram (g)	1 kg = 1000 g
Pound (lb)	1 lb = 0.453592 kg
🚀 Capabilities Implemented
1️⃣ Equality Comparison

Weight objects can be compared across units.
Example:

1 kg == 1000 g

2.20462 lb == 1 kg

2️⃣ Unit Conversion

Weights can be converted between all units.
Examples:

kg → g

g → lb

lb → kg

3️⃣ Addition Operations

Two weights can be added:

Result in first operand unit

Result in explicit target unit

Examples:

1 kg + 1000 g = 2 kg

1 kg + 1000 g (GRAM) = 2000 g

4️⃣ Category Type Safety

Weight and Length cannot be compared.
Example:

1 kg != 1 foot

5️⃣ Immutability & Precision

All operations return new objects

Round-trip conversions maintain accuracy

Works with zero, negative & large values

🧠 Key Learning

Multiple measurement categories design

Reusable enum-based conversion architecture

Type safety across domains

Arithmetic on Value Objects

## 🔹 UC10 — Generic Quantity Measurement using Interface & Generics

In this UC, the application was refactored to a generic architecture to support multiple measurement types using a common design.

🎯 What was implemented
1️⃣ Introduced a common interface

Created IMeasurable interface to standardize unit behavior:

Conversion to base unit

Conversion from base unit

Unit name access

This allows any future unit type (Temperature, Volume, etc.) to plug into the system easily.

2️⃣ Refactored Unit Enums

Both enums now implement IMeasurable:

LengthUnit

WeightUnit

Each unit now defines:

Conversion factor to base unit

Conversion logic

3️⃣ Created Generic Quantity Class

Introduced reusable generic class:

Quantity<U extends IMeasurable>

Capabilities:

Compare quantities across units

Convert between units

Add quantities

Add quantities with target unit

Validation & immutability

This removed duplication and made the design scalable and extensible.

4️⃣ Multi-Domain Support

Application now supports:

Length conversions & arithmetic

Weight conversions & arithmetic

5️⃣ Extensive Test Coverage

Added 30+ unit tests covering:

Enum conversion logic

Equality checks

Conversions

Addition

Null & invalid inputs

HashCode & immutability

Backward compatibility
