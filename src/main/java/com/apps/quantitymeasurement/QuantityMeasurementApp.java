package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	// Equality demo
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	// Comparison demo
	public static boolean demonstrateLengthComparison(double v1, Length.LengthUnit u1, double v2,
			Length.LengthUnit u2) {
		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);
		return demonstrateLengthEquality(l1, l2);
	}

	// UC5 MAIN FEATURE
	// Static conversion API
	public static double convert(double value, Length.LengthUnit from, Length.LengthUnit to) {

		if (from == null || to == null)
			throw new IllegalArgumentException("Units cannot be null");

		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Invalid numeric value");

		double baseValue = value * from.getConversionFactor();
		return baseValue / to.getConversionFactor();
	}

	// Overloaded conversion method (using Length object)
	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit targetUnit) {
		return length.convertTo(targetUnit);
	}

	// Overloaded conversion method (raw values)
	public static Length demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {
		return new Length(value, from).convertTo(to);
	}

	public static void main(String[] args) {
		System.out.println(convert(1, Length.LengthUnit.FEET, Length.LengthUnit.INCHES)); // 12
		System.out.println(convert(3, Length.LengthUnit.YARDS, Length.LengthUnit.FEET)); // 9
		System.out.println(convert(36, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS)); // 1
	}
}