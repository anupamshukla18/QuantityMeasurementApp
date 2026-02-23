package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	// Feet = Feet (same value)
	@Test
	public void testEquality_FeetToFeet_SameValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(1.0, Length.LengthUnit.FEET);
		assertTrue(l1.equals(l2));
	}

	// Inches = Inches (same value)
	@Test
	public void testEquality_InchToInch_SameValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
		assertTrue(l1.equals(l2));
	}

	// 1 foot = 12 inches
	@Test
	public void testEquality_FeetToInch_EquivalentValue() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertTrue(feet.equals(inches));
	}

	// symmetry check
	@Test
	public void testEquality_InchToFeet_EquivalentValue() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertTrue(inches.equals(feet));
	}

	// different feet values
	@Test
	public void testEquality_FeetToFeet_DifferentValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(2.0, Length.LengthUnit.FEET);
		assertFalse(l1.equals(l2));
	}

	// different inches values
	@Test
	public void testEquality_InchToInch_DifferentValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length l2 = new Length(2.0, Length.LengthUnit.INCHES);
		assertFalse(l1.equals(l2));
	}

	// null comparison
	@Test
	public void testEquality_NullComparison() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		assertFalse(l1.equals(null));
	}

	// reflexive property
	@Test
	public void testEquality_SameReference() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		assertTrue(l1.equals(l1));
	}

	// UC4 test cases
	@Test
	public void testEquality_YardToYard_SameValue() {
		Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
		Length l2 = new Length(1.0, Length.LengthUnit.YARDS);
		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_YardToFeet_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		assertTrue(yard.equals(feet));
	}

	@Test
	public void testEquality_FeetToYard_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		assertTrue(feet.equals(yard));
	}

	@Test
	public void testEquality_YardToInches_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);
		assertTrue(yard.equals(inches));
	}

	@Test
	public void testEquality_InchesToYard_EquivalentValue() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);
		assertTrue(inches.equals(yard));
	}

	@Test
	public void testEquality_CentimeterToInches_EquivalentValue() {
		Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length inch = new Length(0.393701, Length.LengthUnit.INCHES);
		assertTrue(cm.equals(inch));
	}

	@Test
	public void testEquality_CentimeterToFeet_NonEquivalentValue() {
		Length cm = new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertFalse(cm.equals(feet));
	}

	@Test
	public void testEquality_TransitiveProperty() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inches));
		assertTrue(yard.equals(inches));
	}
}