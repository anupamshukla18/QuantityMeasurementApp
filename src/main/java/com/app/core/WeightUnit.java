package com.app.core;

public enum WeightUnit implements IMeasurable {
	GRAM(1.0), KILOGRAM(1000.0), TONNE(1000000.0);

	private final double factor;

	WeightUnit(double factor) {
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