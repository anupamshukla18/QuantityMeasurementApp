package com.app.core;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
	CELSIUS(v -> v, v -> v), FAHRENHEIT(v -> (v - 32) * 5 / 9, v -> (v * 9 / 5) + 32);

	private final Function<Double, Double> toBase;
	private final Function<Double, Double> fromBase;

	TemperatureUnit(Function<Double, Double> toBase, Function<Double, Double> fromBase) {
		this.toBase = toBase;
		this.fromBase = fromBase;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return toBase.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return fromBase.apply(baseValue);
	}

	@Override
	public boolean supportsArithmetic() {
		return false;
	}
}