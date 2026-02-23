package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

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

	// UC6 tests

	@Test
	public void addFeetAndInches() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		Length expected = new Length(2.0, Length.LengthUnit.FEET);

		assertTrue(result.equals(expected));
	}

	@Test
	public void addSameUnitFeet() {
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(2.0, Length.LengthUnit.FEET);

		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		Length expected = new Length(3.0, Length.LengthUnit.FEET);

		assertTrue(result.equals(expected));
	}

	@Test
	public void addYardAndFeet() {
		Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
		Length l2 = new Length(3.0, Length.LengthUnit.FEET);

		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		Length expected = new Length(2.0, Length.LengthUnit.YARDS);

		assertTrue(result.equals(expected));
	}

	@Test
	public void addWithZero() {
		Length l1 = new Length(5.0, Length.LengthUnit.FEET);
		Length l2 = new Length(0.0, Length.LengthUnit.INCHES);

		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		Length expected = new Length(5.0, Length.LengthUnit.FEET);

		assertTrue(result.equals(expected));
	}

	@Test
	public void addNegativeValues() {
		Length l1 = new Length(5.0, Length.LengthUnit.FEET);
		Length l2 = new Length(-2.0, Length.LengthUnit.FEET);

		Length result = QuantityMeasurementApp.demonstrateLengthAddition(l1, l2);
		Length expected = new Length(3.0, Length.LengthUnit.FEET);

		assertTrue(result.equals(expected));
	}
}