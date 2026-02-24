package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	// Constructor
	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		if (Double.isNaN(value))
			throw new IllegalArgumentException("Value cannot be NaN");

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	// Central Rounding Helper
	private double roundTwoDecimals(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	// Arithmetic Operation Enum
	private enum ArithmeticOperation {
		ADD((a, b) -> a + b),

		SUBTRACT((a, b) -> a - b),

		DIVIDE((a, b) -> {
			if (b == 0.0)
				throw new ArithmeticException("Cannot divide by zero");
			return a / b;
		});

		private final DoubleBinaryOperator operation;

		ArithmeticOperation(DoubleBinaryOperator operation) {
			this.operation = operation;
		}

		public double compute(double a, double b) {
			return operation.applyAsDouble(a, b);
		}
	}

	// Central Validation Helper
	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {

		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null");

		if (!this.unit.getClass().equals(other.unit.getClass()))
			throw new IllegalArgumentException("Incompatible measurement categories");

		if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
			throw new IllegalArgumentException("Values must be finite numbers");

		if (targetUnitRequired && targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");
	}

	// Core Arithmetic Engine
	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

		double thisBase = this.unit.convertToBaseUnit(this.value);
		double otherBase = other.unit.convertToBaseUnit(other.value);

		return operation.compute(thisBase, otherBase);
	}

	// Conversion
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	public Quantity<U> convertTo(U targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = toBaseUnit();
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<>(convertedValue, targetUnit);
	}

	// ADD
	public Quantity<U> add(Quantity<U> other) {
		validateArithmeticOperands(other, null, false);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double finalValue = this.unit.convertFromBaseUnit(baseResult);

		return new Quantity<>(roundTwoDecimals(finalValue), this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double finalValue = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(roundTwoDecimals(finalValue), targetUnit);
	}

	// SUBTRACT
	public Quantity<U> subtract(Quantity<U> other) {
		validateArithmeticOperands(other, null, false);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double finalValue = this.unit.convertFromBaseUnit(baseResult);

		return new Quantity<>(roundTwoDecimals(finalValue), this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double finalValue = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(roundTwoDecimals(finalValue), targetUnit);
	}

	// DIVIDE
	public double divide(Quantity<U> other) {
		validateArithmeticOperands(other, null, false);

		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	// Equality & Hashing
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Quantity<?> other = (Quantity<?>) obj;

		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 0.01;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Math.round(toBaseUnit() * 100.0) / 100.0);
	}

	@Override
	public String toString() {
		return value + " " + unit.getUnitName();
	}
}