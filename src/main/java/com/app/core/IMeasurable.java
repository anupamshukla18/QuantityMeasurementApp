package com.app.core;
 
public interface IMeasurable {
	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	default boolean supportsArithmetic() {
		return true;
	}
}
