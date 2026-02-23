package com.apps.quantitymeasurement;

public class Length {

	private final double value;
	private final LengthUnit unit;

	// Base unit = INCHES
	public enum LengthUnit {
		FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

		private final double conversionFactor;

		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	public Length(double value, LengthUnit unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Invalid numeric value");

		this.value = value;
		this.unit = unit;
	}

	// Convert to base unit (INCHES)
	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	private boolean compare(Length other) {
		double a = this.convertToBaseUnit();
		double b = other.convertToBaseUnit();
		return Math.abs(a - b) < 0.0001;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Length other = (Length) obj;
		return compare(other);
	}

	// UC5 NEW FEATURE → Instance conversion
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = convertToBaseUnit();
		double convertedValue = baseValue / targetUnit.getConversionFactor();
		return new Length(convertedValue, targetUnit);
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}
}