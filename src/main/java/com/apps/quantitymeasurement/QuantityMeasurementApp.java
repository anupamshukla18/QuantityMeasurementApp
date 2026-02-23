package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	// Generic equality method
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	// Feet equality demo
	public static void demonstrateFeetEquality() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(1.0, Length.LengthUnit.FEET);
		System.out.println("Feet equal? " + demonstrateLengthEquality(l1, l2));
	}

	// Inches equality demo
	public static void demonstrateInchesEquality() {
		Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
		System.out.println("Inches equal? " + demonstrateLengthEquality(l1, l2));
	}

	// Feet & Inches comparison demo
	public static void demonstrateFeetInchesComparison() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
		System.out.println("Feet & Inches equal? " + demonstrateLengthEquality(l1, l2));
	}

	public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateInchesEquality();
		demonstrateFeetInchesComparison();
	}
}