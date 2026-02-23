package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	// ================= UC5 CONVERSION TESTS =================

	@Test
	public void testConversion_FeetToInches() {
		assertEquals(12.0, QuantityMeasurementApp.convert(1, Length.LengthUnit.FEET, Length.LengthUnit.INCHES), 0.0001);
	}

	@Test
	public void testConversion_InchesToFeet() {
		assertEquals(2.0, QuantityMeasurementApp.convert(24, Length.LengthUnit.INCHES, Length.LengthUnit.FEET), 0.0001);
	}

	@Test
	public void testConversion_YardsToInches() {
		assertEquals(36.0, QuantityMeasurementApp.convert(1, Length.LengthUnit.YARDS, Length.LengthUnit.INCHES),
				0.0001);
	}

	@Test
	public void testConversion_InchesToYards() {
		assertEquals(2.0, QuantityMeasurementApp.convert(72, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS),
				0.0001);
	}

	@Test
	public void testConversion_CentimetersToInches() {
		assertEquals(1.0, QuantityMeasurementApp.convert(2.54, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES),
				0.01);
	}

	@Test
	public void testRoundTripConversion() {
		double value = 5;
		double result = QuantityMeasurementApp.convert(
				QuantityMeasurementApp.convert(value, Length.LengthUnit.FEET, Length.LengthUnit.INCHES),
				Length.LengthUnit.INCHES, Length.LengthUnit.FEET);
		assertEquals(value, result, 0.0001);
	}

	@Test
	public void testZeroValueConversion() {
		assertEquals(0.0, QuantityMeasurementApp.convert(0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES), 0.0001);
	}

	@Test
	public void testNegativeValueConversion() {
		assertEquals(-12.0, QuantityMeasurementApp.convert(-1, Length.LengthUnit.FEET, Length.LengthUnit.INCHES),
				0.0001);
	}

	@Test
	public void testInvalidUnitThrows() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.convert(1, null, Length.LengthUnit.FEET));
	}

	@Test
	public void testNaNThrows() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.convert(Double.NaN, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
	}

	// ================= UC3/UC4 EQUALITY TESTS =================

	@Test
	public void testFeetEquality() {
		Length f1 = new Length(1, Length.LengthUnit.FEET);
		Length f2 = new Length(1, Length.LengthUnit.FEET);
		assertTrue(f1.equals(f2));
	}

	@Test
	public void testInchesEquality() {
		Length i1 = new Length(12, Length.LengthUnit.INCHES);
		Length f1 = new Length(1, Length.LengthUnit.FEET);
		assertTrue(i1.equals(f1));
	}

	@Test
	public void testCrossUnitInequality() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(10, Length.LengthUnit.INCHES);
		assertFalse(l1.equals(l2));
	}

	// ================= UC6 ADDITION TESTS =================

	@Test
	public void testAddition_SameUnit_Feet() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(2, Length.LengthUnit.FEET);
		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		assertTrue(result.equals(new Length(3, Length.LengthUnit.FEET)));
	}

	@Test
	public void testAddition_CrossUnit_FeetPlusInches() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		assertTrue(result.equals(new Length(2, Length.LengthUnit.FEET)));
	}

	@Test
	public void testAddition_Commutative() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		assertTrue(l1.add(l2).equals(l2.add(l1)));
	}

	@Test
	public void testAddition_WithZero() {
		Length l1 = new Length(5, Length.LengthUnit.FEET);
		Length l2 = new Length(0, Length.LengthUnit.INCHES);
		assertTrue(l1.add(l2).equals(new Length(5, Length.LengthUnit.FEET)));
	}

	@Test
	public void testAddition_NegativeValues() {
		Length l1 = new Length(5, Length.LengthUnit.FEET);
		Length l2 = new Length(-2, Length.LengthUnit.FEET);
		assertTrue(l1.add(l2).equals(new Length(3, Length.LengthUnit.FEET)));
	}

	// ================= UC7 ADDITION WITH TARGET UNIT =================

	@Test
	public void testAddition_ExplicitTarget_Feet() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, Length.LengthUnit.FEET);
		assertTrue(result.equals(new Length(2, Length.LengthUnit.FEET)));
	}

	@Test
	public void testAddition_ExplicitTarget_Inches() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, Length.LengthUnit.INCHES);
		assertTrue(result.equals(new Length(24, Length.LengthUnit.INCHES)));
	}

	@Test
	public void testAddition_ExplicitTarget_Yards() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, Length.LengthUnit.YARDS);
		assertTrue(result.equals(new Length(0.6667, Length.LengthUnit.YARDS)));
	}

	@Test
	public void testAddition_TargetUnitNullThrows() {
		Length l1 = new Length(1, Length.LengthUnit.FEET);
		Length l2 = new Length(12, Length.LengthUnit.INCHES);
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthAddition(l1, l2, null));
	}

}