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
			throw new IllegalArgumentException("Invalid length input");
		}
		this.value = value;
		this.unit = unit;
	}

	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	private double convertFromBaseToTargetUnit(double baseValue, LengthUnit targetUnit) {
		return baseValue / targetUnit.getConversionFactor();
	}

	private boolean compare(Length that) {
		return Math.abs(this.convertToBaseUnit() - that.convertToBaseUnit()) < 0.01;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Length))
			return false;
		return compare((Length) o);
	}

	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException();

		double baseValue = convertToBaseUnit();
		double converted = convertFromBaseToTargetUnit(baseValue, targetUnit);
		return new Length(converted, targetUnit);
	}

	// ---------------- UC6 ----------------
	public Length add(Length thatLength) {
		if (thatLength == null)
			throw new IllegalArgumentException();

		double baseSum = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
		double result = convertFromBaseToTargetUnit(baseSum, this.unit);

		return new Length(result, this.unit);
	}

	// ---------------- UC7 NEW ----------------
	public Length add(Length thatLength, LengthUnit targetUnit) {
		if (thatLength == null || targetUnit == null) {
			throw new IllegalArgumentException();
		}

		double baseSum = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
		double result = convertFromBaseToTargetUnit(baseSum, targetUnit);

		return new Length(result, targetUnit);
	}

	@Override
	public String toString() {
		return value + " " + unit;
	}
}