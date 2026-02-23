package com.apps.quantitymeasurement;

public class Length {

	private double value;
	private LengthUnit unit;

	// Enum inside class
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

	// Constructor
	public Length(double value, LengthUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	// Convert to base unit (INCHES)
	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	// Compare method
	public boolean compare(Length thatLength) {
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}

	// equals() override
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Length that = (Length) obj;

		return compare(that);
	}

	// main for standalone testing
	public static void main(String[] args) {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);

		System.out.println("Are lengths equal? " + length1.equals(length2));
	}
}