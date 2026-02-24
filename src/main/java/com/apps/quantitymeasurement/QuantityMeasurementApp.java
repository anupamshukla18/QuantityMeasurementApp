package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	// generic equality demo
	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {

		return quantity1.equals(quantity2);
	}

	// generic conversion demo
	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		return quantity.convertTo(targetUnit);
	}

	// generic addition (implicit target unit)
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,
			Quantity<U> quantity2) {

		return quantity1.add(quantity2);
	}

	// generic addition (explicit target unit)
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2,
			U targetUnit) {

		return quantity1.add(quantity2, targetUnit);
	}

	// subtraction demo (implicit target unit)
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
		return q1.subtract(q2);
	}

	// subtraction demo (explicit target unit)
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2,
			U targetUnit) {
		return q1.subtract(q2, targetUnit);
	}

	// division demo
	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
		return q1.divide(q2);
	}

	public static void main(String[] args) {

		// ================= LENGTH DEMO =================
		Quantity<LengthUnit> length1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> length2 = new Quantity<>(12.0, LengthUnit.INCHES);

		System.out.println("Length Equality: " + demonstrateEquality(length1, length2));
		System.out.println("Length Conversion: " + demonstrateConversion(length1, LengthUnit.INCHES));
		System.out.println("Length Addition: " + demonstrateAddition(length1, length2));

		// ================= WEIGHT DEMO =================
		Quantity<WeightUnit> weight1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		System.out.println("Weight Equality: " + demonstrateEquality(weight1, weight2));
		System.out.println("Weight Conversion: " + demonstrateConversion(weight1, WeightUnit.GRAM));
		System.out.println("Weight Addition: " + demonstrateAddition(weight1, weight2));

		// ================= UC11 VOLUME DEMO =================
		Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> volume3 = new Quantity<>(1.0, VolumeUnit.GALLON);

		// Equality
		System.out.println("Volume Equality L↔mL: " + demonstrateEquality(volume1, volume2));
		System.out.println("Volume Equality L↔Gallon: " + demonstrateEquality(volume1, volume3));

		// Conversion
		System.out.println("Convert Litre → mL: " + demonstrateConversion(volume1, VolumeUnit.MILLILITRE));
		System.out.println("Convert Gallon → Litre: " + demonstrateConversion(volume3, VolumeUnit.LITRE));

		// Addition (implicit target unit)
		System.out.println("Add L + mL: " + demonstrateAddition(volume1, volume2));

		// Addition (explicit target unit)
		System.out.println("Add L + Gallon → mL: " + demonstrateAddition(volume1, volume3, VolumeUnit.MILLILITRE));

		System.out.println("==== Quantity Measurement Demo ====\n");

		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		// ---------- Equality ----------
		System.out.println("Equality:");
		System.out.println(demonstrateEquality(q1, q2));

		// ---------- Conversion ----------
		System.out.println("\nConversion:");
		System.out.println(demonstrateConversion(q1, LengthUnit.INCHES));

		// ---------- Addition ----------
		System.out.println("\nAddition:");
		System.out.println(demonstrateAddition(q1, q2));
		System.out.println(demonstrateAddition(q1, q2, LengthUnit.INCHES));

		// ---------- Subtraction ----------
		System.out.println("\nSubtraction:");
		System.out.println(demonstrateSubtraction(q1, q2));
		System.out.println(demonstrateSubtraction(q1, q2, LengthUnit.INCHES));

		// ---------- Division ----------
		System.out.println("\nDivision:");
		System.out.println(demonstrateDivision(q1, q2));
	}
}