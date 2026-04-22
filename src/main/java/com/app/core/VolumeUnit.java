package com.app.core;

public enum VolumeUnit implements IMeasurable {
	LITER(1.0), GALLON(3.78541), MILLILITER(0.001);

	private final double factor;

	VolumeUnit(double factor) {
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