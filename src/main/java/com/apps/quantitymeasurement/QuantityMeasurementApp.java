package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	// ---------- UC5 ----------
	public static double convert(double value, Length.LengthUnit source, Length.LengthUnit target) {

		if (source == null || target == null || !Double.isFinite(value)) {
			throw new IllegalArgumentException();
		}

		double baseValue = value * source.getConversionFactor();
		return baseValue / target.getConversionFactor();
	}

	// ---------- Equality ----------
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double v1, Length.LengthUnit u1, double v2,
			Length.LengthUnit u2) {
		return new Length(v1, u1).equals(new Length(v2, u2));
	}

	// ---------- Conversion ----------
	public static Length demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {
		return new Length(value, from).convertTo(to);
	}

	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	// ---------- UC6 ----------
	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		return l1.add(l2);
	}

	// ---------- UC7 NEW ----------
	public static Length demonstrateLengthAddition(Length l1, Length l2, Length.LengthUnit targetUnit) {
		return l1.add(l2, targetUnit);
	}

	public static void main(String[] args) {

		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

		Length result1 = demonstrateLengthAddition(l1, l2);
		System.out.println(result1);

		Length result2 = demonstrateLengthAddition(l1, l2, Length.LengthUnit.YARDS);
		System.out.println(result2);
	}
}