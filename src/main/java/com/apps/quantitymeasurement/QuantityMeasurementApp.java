package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	// Inner class to represent Feet measurement
	public static class Feet {

		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {

			// 1 Same reference check
			if (this == obj)
				return true;

			// 2️ Null check
			if (obj == null)
				return false;

			// 3️ Type check
			if (getClass() != obj.getClass())
				return false;

			// 4️ Cast safely
			Feet other = (Feet) obj;

			// 5️ Compare double values safely
			return Double.compare(this.value, other.value) == 0;
		}
	}
}