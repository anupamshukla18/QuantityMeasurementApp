package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	// ================================
	// LENGTH ENUM TESTS
	// ================================

	@Test
	public void testLengthUnitConversionFactor() {
		assertEquals(12.0, LengthUnit.FEET.getConversionFactor(), 0.0001);
		assertEquals(1.0, LengthUnit.INCHES.getConversionFactor(), 0.0001);
		assertEquals(36.0, LengthUnit.YARDS.getConversionFactor(), 0.0001);
		assertEquals(0.393701, LengthUnit.CENTIMETERS.getConversionFactor(), 0.0001);
	}

	@Test
	public void testLengthConvertToBase() {
		assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(1), 0.01);
		assertEquals(12.0, LengthUnit.INCHES.convertToBaseUnit(12), 0.01);
		assertEquals(36.0, LengthUnit.YARDS.convertToBaseUnit(1), 0.01);
		assertEquals(12.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 0.5);
	}

	@Test
	public void testLengthConvertFromBase() {
		assertEquals(1.0, LengthUnit.FEET.convertFromBaseUnit(12), 0.01);
		assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(12), 0.01);
		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(36), 0.01);
	}

	// ================================
	// WEIGHT ENUM TESTS
	// ================================

	@Test
	public void testWeightUnitConversionFactor() {
		assertEquals(0.001, WeightUnit.MILLIGRAM.getConversionFactor(), 0.0001);
		assertEquals(1.0, WeightUnit.GRAM.getConversionFactor(), 0.0001);
		assertEquals(1000.0, WeightUnit.KILOGRAM.getConversionFactor(), 0.0001);
		assertEquals(453.592, WeightUnit.POUND.getConversionFactor(), 0.0001);
		assertEquals(1_000_000.0, WeightUnit.TONNE.getConversionFactor(), 0.0001);
	}

	@Test
	public void testWeightConvertToBase() {
		assertEquals(1000.0, WeightUnit.KILOGRAM.convertToBaseUnit(1), 0.01);
		assertEquals(1000.0, WeightUnit.GRAM.convertToBaseUnit(1000), 0.01);
	}

	@Test
	public void testWeightConvertFromBase() {
		assertEquals(1.0, WeightUnit.KILOGRAM.convertFromBaseUnit(1000), 0.01);
	}

	// ================================
	// QUANTITY EQUALITY TESTS
	// ================================

	@Test
	public void testLengthEquality() {
		assertTrue(new Quantity<>(1, LengthUnit.FEET).equals(new Quantity<>(12, LengthUnit.INCHES)));
	}

	@Test
	public void testWeightEquality() {
		assertTrue(new Quantity<>(1, WeightUnit.KILOGRAM).equals(new Quantity<>(1000, WeightUnit.GRAM)));
	}

	@Test
	public void testNotEqualDifferentValues() {
		assertFalse(new Quantity<>(1, LengthUnit.FEET).equals(new Quantity<>(10, LengthUnit.INCHES)));
	}

	@Test
	public void testEqualsNull() {
		assertFalse(new Quantity<>(1, LengthUnit.FEET).equals(null));
	}

	@Test
	public void testEqualsDifferentType() {
		assertFalse(new Quantity<>(1, LengthUnit.FEET).equals("test"));
	}

	@Test
	public void testSameReference() {
		Quantity<LengthUnit> q = new Quantity<>(1, LengthUnit.FEET);
		assertTrue(q.equals(q));
	}

	// ================================
	// CONVERSION TESTS
	// ================================

	@Test
	public void testLengthConversion() {
		Quantity<LengthUnit> result = new Quantity<>(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

		assertEquals(12, result.getValue(), 0.01);
	}

	@Test
	public void testWeightConversion() {
		Quantity<WeightUnit> result = new Quantity<>(1, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000, result.getValue(), 0.01);
	}

	@Test
	public void testRoundTripConversion() {
		Quantity<LengthUnit> original = new Quantity<>(5, LengthUnit.FEET);

		Quantity<LengthUnit> result = original.convertTo(LengthUnit.INCHES).convertTo(LengthUnit.FEET);

		assertTrue(original.equals(result));
	}

	// ================================
	// ADDITION TESTS
	// ================================

	@Test
	public void testLengthAddition() {
		Quantity<LengthUnit> result = new Quantity<>(1, LengthUnit.FEET).add(new Quantity<>(12, LengthUnit.INCHES));

		assertTrue(result.equals(new Quantity<>(2, LengthUnit.FEET)));
	}

	@Test
	public void testLengthAdditionTargetUnit() {
		Quantity<LengthUnit> result = new Quantity<>(1, LengthUnit.FEET).add(new Quantity<>(12, LengthUnit.INCHES),
				LengthUnit.INCHES);

		assertTrue(result.equals(new Quantity<>(24, LengthUnit.INCHES)));
	}

	@Test
	public void testWeightAddition() {
		Quantity<WeightUnit> result = new Quantity<>(1, WeightUnit.KILOGRAM).add(new Quantity<>(1000, WeightUnit.GRAM));

		assertTrue(result.equals(new Quantity<>(2, WeightUnit.KILOGRAM)));
	}

	// ================================
	// NULL & INVALID TESTS
	// ================================

	@Test
	public void testConstructorNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1, null));
	}

	@Test
	public void testConstructorNaN() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	@Test
	public void testConvertToNull() {
		Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> length.convertTo(null));
	}

	@Test
	public void testAddNull() {
		Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> length.add(null));
	}

	@Test
	public void testAddNullTarget() {
		Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
	}

	// ================================
	// HASHCODE TEST
	// ================================

	@Test
	public void testHashCodeConsistency() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		assertEquals(q1.hashCode(), q2.hashCode());
	}

	// ================================
	// IMMUTABILITY TEST
	// ================================

	@Test
	public void testImmutability() {
		Quantity<LengthUnit> l1 = new Quantity<>(1, LengthUnit.FEET);

		Quantity<LengthUnit> result = l1.convertTo(LengthUnit.INCHES);

		assertNotEquals(l1.getUnit(), result.getUnit());
	}

	// UC 11 test cases

	// ================= UC11 VOLUME TESTS =================

	// 1
	@Test
	public void testEquality_LitreToLitre_SameValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(q1.equals(q2));
	}

	// 2
	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		assertFalse(q1.equals(q2));
	}

	// 3
	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertTrue(q1.equals(q2));
	}

	// 4
	@Test
	public void testEquality_MillilitreToLitre_EquivalentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(q1.equals(q2));
	}

	// 5
	@Test
	public void testEquality_LitreToGallon_EquivalentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.GALLON);
		assertTrue(q1.equals(q2));
	}

	// 6
	@Test
	public void testEquality_GallonToLitre_EquivalentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		assertTrue(q1.equals(q2));
	}

	// 7
	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(volume.equals(length));
	}

	// 8
	@Test
	public void testEquality_VolumeVsWeight_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertFalse(volume.equals(weight));
	}

	// 9
	@Test
	public void testEquality_NullComparison() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertFalse(volume.equals(null));
	}

	// 10
	@Test
	public void testEquality_SameReference() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(volume.equals(volume));
	}

	// 11
	@Test
	public void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Quantity<>(1.0, null);
		});
	}

	// 12
	@Test
	public void testEquality_TransitiveProperty() {
		Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);

		assertTrue(a.equals(b));
		assertTrue(b.equals(c));
		assertTrue(a.equals(c));
	}

	// 13
	@Test
	public void testEquality_ZeroValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
		assertTrue(q1.equals(q2));
	}

	// 14
	@Test
	public void testEquality_NegativeVolume() {
		Quantity<VolumeUnit> q1 = new Quantity<>(-1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);
		assertTrue(q1.equals(q2));
	}

	// 15
	@Test
	public void testEquality_LargeVolumeValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1000000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.LITRE);
		assertTrue(q1.equals(q2));
	}

	// 16
	@Test
	public void testEquality_SmallVolumeValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.001, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.MILLILITRE);
		assertTrue(q1.equals(q2));
	}

	// 17
	@Test
	public void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), q.convertTo(VolumeUnit.MILLILITRE));
	}

	// 18
	@Test
	public void testConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> q = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), q.convertTo(VolumeUnit.LITRE));
	}

	// 19
	@Test
	public void testConversion_GallonToLitre() {
		Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.GALLON);
		assertEquals(new Quantity<>(3.78541, VolumeUnit.LITRE), q.convertTo(VolumeUnit.LITRE));
	}

	// 20
	@Test
	public void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> q = new Quantity<>(3.78541, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(1.0, VolumeUnit.GALLON), q.convertTo(VolumeUnit.GALLON));
	}

	// 21
	@Test
	public void testConversion_MillilitreToGallon() {
		Quantity<VolumeUnit> q = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.GALLON);
		assertEquals(new Quantity<>(0.264172, VolumeUnit.GALLON), result);
	}

	// 22
	@Test
	public void testConversion_SameUnit() {
		Quantity<VolumeUnit> q = new Quantity<>(5.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(5.0, VolumeUnit.LITRE), q.convertTo(VolumeUnit.LITRE));
	}

	// 23
	@Test
	public void testConversion_ZeroValue() {
		Quantity<VolumeUnit> q = new Quantity<>(0.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(0.0, VolumeUnit.MILLILITRE), q.convertTo(VolumeUnit.MILLILITRE));
	}

	// 24
	@Test
	public void testConversion_NegativeValue() {
		Quantity<VolumeUnit> q = new Quantity<>(-1.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE), q.convertTo(VolumeUnit.MILLILITRE));
	}

	// 25
	@Test
	public void testConversion_RoundTrip() {
		Quantity<VolumeUnit> q = new Quantity<>(1.5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertTrue(q.equals(result));
	}

	// 26
	@Test
	public void testAddition_SameUnit_LitrePlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), q1.add(q2));
	}

	// 27
	@Test
	public void testAddition_SameUnit_MillilitrePlusMillilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), q1.add(q2));
	}

	// 28
	@Test
	public void testAddition_CrossUnit_LitrePlusMillilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), q1.add(q2));
	}

	// 29
	@Test
	public void testAddition_CrossUnit_MillilitrePlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE), q1.add(q2));
	}

	// 30
	@Test
	public void testAddition_CrossUnit_GallonPlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), q1.add(q2));
	}

	// 31
	@Test
	public void testAddition_WithTargetUnit_Litre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(4.78541, VolumeUnit.LITRE), result);
	}

	// 32
	@Test
	public void testAddition_WithTargetUnit_Millilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(4785.41, VolumeUnit.MILLILITRE), result);
	}

	// 33
	@Test
	public void testAddition_WithTargetUnit_Gallon() {
		Quantity<VolumeUnit> q1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.GALLON);
		assertEquals(new Quantity<>(2.0, VolumeUnit.GALLON), result);
	}

	// 34
	@Test
	public void testAddition_NullOtherQuantity() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertThrows(IllegalArgumentException.class, () -> q1.add(null));
	}

	// 35
	@Test
	public void testAddition_NullTargetUnit() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
	}

	// 36
	@Test
	public void testAddition_IncompatibleCategory_LengthVsVolume() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> volume.add((Quantity) length));
	}

	// 37
	@Test
	public void testAddition_ZeroValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), q1.add(q2));
	}

	// 38
	@Test
	public void testAddition_NegativeValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(-1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(0.0, VolumeUnit.LITRE), q1.add(q2));
	}

	// 39
	@Test
	public void testRoundTrip_AddThenConvert() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result = q1.add(q2).convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertTrue(result.equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
	}

	// 40
	@Test
	public void testEqualityAfterAdditionDifferentUnits() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);

		Quantity<VolumeUnit> sum = q1.add(q2);

		assertTrue(sum.equals(new Quantity<>(2.0, VolumeUnit.GALLON)));
	}

	// 41
	@Test
	public void testVolume_Immutability_AfterConversion() {
		Quantity<VolumeUnit> original = new Quantity<>(1.0, VolumeUnit.LITRE);
		original.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), original);
	}

	// 42
	@Test
	public void testVolume_Immutability_AfterAddition() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		q1.add(q2);
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), q1);
	}

	// 43
	@Test
	public void testEnum_Accessibility_Litre() {
		assertNotNull(VolumeUnit.valueOf("LITRE"));
	}

	// 44
	@Test
	public void testEnum_Accessibility_Millilitre() {
		assertNotNull(VolumeUnit.valueOf("MILLILITRE"));
	}

	// 45
	@Test
	public void testEnum_Accessibility_Gallon() {
		assertNotNull(VolumeUnit.valueOf("GALLON"));
	}

	// 46
	@Test
	public void testVolume_LargeValuesAddition() {
		Quantity<VolumeUnit> q1 = new Quantity<>(10000.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(10000.0, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(20000.0, VolumeUnit.LITRE), q1.add(q2));
	}

	// 47
	@Test
	public void testVolume_SmallValuesAddition() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.001, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.001, VolumeUnit.LITRE);
		assertEquals(new Quantity<>(0.002, VolumeUnit.LITRE), q1.add(q2));
	}

	// 48
	@Test
	public void testVolume_DoublePrecisionAccuracy() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.1, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.2, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(new Quantity<>(0.3, VolumeUnit.LITRE), result);
	}

	// 49
	@Test
	public void testVolume_ConversionPrecision() {
		Quantity<VolumeUnit> q = new Quantity<>(2.5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(new Quantity<>(2500.0, VolumeUnit.MILLILITRE), result);
	}

	// 50
	@Test
	public void testVolume_AdditionPrecisionAcrossUnits() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.5, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.892705, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertTrue(result.equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
	}

	// ================= UC12 SUBTRACTION TESTS =================

	@Test
	public void testSubtract_FeetAndInches() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertTrue(result.equals(new Quantity<>(1, LengthUnit.FEET)));
	}

	@Test
	public void testSubtract_InchesFromFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertTrue(result.equals(new Quantity<>(0.5, LengthUnit.FEET)));
	}

	@Test
	public void testSubtract_SameUnits() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(3, LengthUnit.FEET);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertTrue(result.equals(new Quantity<>(7, LengthUnit.FEET)));
	}

	@Test
	public void testSubtract_ResultZero() {
		Quantity<LengthUnit> q1 = new Quantity<>(12, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertTrue(result.equals(new Quantity<>(0, LengthUnit.INCHES)));
	}

	@Test
	public void testSubtract_WithTargetUnitFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.FEET);

		assertTrue(result.equals(new Quantity<>(1, LengthUnit.FEET)));
	}

	@Test
	public void testSubtract_WithTargetUnitInches() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.INCHES);

		assertTrue(result.equals(new Quantity<>(12, LengthUnit.INCHES)));
	}

	@Test
	public void testSubtract_WithTargetUnitYards() {
		Quantity<LengthUnit> q1 = new Quantity<>(6, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(3, LengthUnit.FEET);

		Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.YARDS);

		assertTrue(result.equals(new Quantity<>(1, LengthUnit.YARDS)));
	}

	// ================= UC12 DIVISION TESTS =================

	@Test
	public void testDivide_FeetByFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2, LengthUnit.FEET);

		double result = q1.divide(q2);

		assertEquals(5.0, result, 0.0001);
	}

	@Test
	public void testDivide_FeetByInches() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6, LengthUnit.INCHES);

		double result = q1.divide(q2);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	public void testDivide_InchesByFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(24, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		double result = q1.divide(q2);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	public void testDivide_YardsByFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(3, LengthUnit.YARDS);
		Quantity<LengthUnit> q2 = new Quantity<>(3, LengthUnit.FEET);

		double result = q1.divide(q2);

		assertEquals(3.0, result, 0.0001);
	}

	@Test
	public void testDivide_ResultOne() {
		Quantity<LengthUnit> q1 = new Quantity<>(12, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		double result = q1.divide(q2);

		assertEquals(1.0, result, 0.0001);
	}

	// ================= UC12 EXCEPTION TESTS =================

	@Test
	public void testSubtract_NullThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
	}

	@Test
	public void testSubtract_WithTargetUnitNullQuantity() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(null, LengthUnit.FEET));
	}

	@Test
	public void testSubtract_WithNullTargetUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
	}

	@Test
	public void testDivide_ByNullThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.divide(null));
	}

	@Test
	public void testDivide_ByZeroThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> q1.divide(q2));
	}

	// ================= UC13 REFACTOR SAFETY =================

	@Test
	public void testAdd_BehaviourUnchanged() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.add(q2);

		assertTrue(result.equals(new Quantity<>(2, LengthUnit.FEET)));
	}

	@Test
	public void testSubtract_BehaviourUnchanged() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertTrue(result.equals(new Quantity<>(1, LengthUnit.FEET)));
	}

	@Test
	public void testDivide_BehaviourUnchanged() {
		Quantity<LengthUnit> q1 = new Quantity<>(24, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		assertEquals(2.0, q1.divide(q2), 0.0001);
	}

	@Test
	public void testAdd_WithTargetUnit_BehaviourUnchanged() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.add(q2, LengthUnit.INCHES);

		assertTrue(result.equals(new Quantity<>(24, LengthUnit.INCHES)));
	}

	@Test
	public void testSubtract_WithTargetUnit_BehaviourUnchanged() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.INCHES);

		assertTrue(result.equals(new Quantity<>(12, LengthUnit.INCHES)));
	}

	// ================= UC13 VALIDATION TESTS =================

	@Test
	public void testAdd_NullOperandThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.add(null));
	}

	@Test
	public void testSubtract_NullOperandThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
	}

	@Test
	public void testDivide_NullOperandThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.divide(null));
	}

	@Test
	public void testAdd_WithNullTargetUnitThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
	}

	@Test
	public void testSubtract_WithNullTargetUnitThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
	}

	@Test
	public void testArithmetic_IncompatibleCategoriesThrowsException() {
		Quantity<LengthUnit> length = new Quantity<>(1, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.add((Quantity) weight));
	}

	@Test
	public void testArithmetic_NonFiniteValueThrowsException() {
		Quantity<LengthUnit> q1 = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.add(q2));
	}

	// ================= UC13 ARITHMETIC ENUM TESTS =================

	@Test
	public void testDivide_ByZeroThrowsArithmeticException() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> q1.divide(q2));
	}

	@Test
	public void testAdditionAcrossUnits_UsesBaseUnitLogic() {
		Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = feet.add(inches);

		assertTrue(result.equals(new Quantity<>(2, LengthUnit.FEET)));
	}

	@Test
	public void testSubtractionAcrossUnits_UsesBaseUnitLogic() {
		Quantity<LengthUnit> feet = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = feet.subtract(inches);

		assertTrue(result.equals(new Quantity<>(1, LengthUnit.FEET)));
	}

	@Test
	public void testDivideAcrossUnits_UsesBaseUnitLogic() {
		Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);

		assertEquals(1.0, feet.divide(inches), 0.0001);
	}

	// ================= UC13 ROUNDING & DRY TESTS =================

	@Test
	public void testAddition_RoundsToTwoDecimals() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.add(q2, LengthUnit.FEET);

		assertEquals(1.08, result.getValue(), 0.001);
	}

	@Test
	public void testSubtraction_RoundsToTwoDecimals() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(1, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.FEET);

		assertEquals(0.92, result.getValue(), 0.001);
	}

	@Test
	public void testAdd_ResultIsNewObject() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.add(q2);

		assertNotSame(q1, result);
		assertNotSame(q2, result);
	}

	@Test
	public void testSubtract_ResultIsNewObject() {
		Quantity<LengthUnit> q1 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);

		Quantity<LengthUnit> result = q1.subtract(q2);

		assertNotSame(q1, result);
	}

	@Test
	public void testConvertTo_DoesNotMutateOriginalObject() {
		Quantity<LengthUnit> original = new Quantity<>(1, LengthUnit.FEET);
		original.convertTo(LengthUnit.INCHES);

		assertEquals(1, original.getValue());
		assertEquals(LengthUnit.FEET, original.getUnit());
	}

	@Test
	public void testEquals_UsesTolerance() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.001, LengthUnit.INCHES);

		assertTrue(q1.equals(q2));
	}
}