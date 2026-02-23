package com.apps.quantitymeasurement;

public class Length {

	private double value;
	private LengthUnit unit;

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
		if (unit == null || !Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid Length input");
		}
		this.value = value;
		this.unit = unit;
	}

	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	private boolean compare(Length that) {
		double a = this.convertToBaseUnit();
		double b = that.convertToBaseUnit();
		return Math.abs(a - b) < 0.01;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Length))
			return false;
		Length that = (Length) o;
		return compare(that);
	}

	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException();

		double baseValue = convertToBaseUnit();
		double converted = convertFromBaseToTargetUnit(baseValue, targetUnit);
		return new Length(converted, targetUnit);
	}

	private double convertFromBaseToTargetUnit(double baseValue, LengthUnit targetUnit) {
		return baseValue / targetUnit.getConversionFactor();
	}

	// UC6 CORE METHOD
	public Length add(Length thatLength) {
		if (thatLength == null)
			throw new IllegalArgumentException("Second operand null");

		double base1 = this.convertToBaseUnit();
		double base2 = thatLength.convertToBaseUnit();

		double sumBase = base1 + base2;

		double resultInThisUnit = convertFromBaseToTargetUnit(sumBase, this.unit);

		return new Length(resultInThisUnit, this.unit);
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}
}