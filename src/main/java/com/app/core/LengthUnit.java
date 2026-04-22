package com.app.core;

public enum LengthUnit implements IMeasurable {
	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETER(0.394);

	private final double factor;

	LengthUnit(double factor) {
		this.factor = factor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * factor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / factor;
	}
} 