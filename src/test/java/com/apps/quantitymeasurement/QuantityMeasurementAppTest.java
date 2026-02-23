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
}