package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double thisValue;
	private String thisUnit;
	private String thisMeasurementType;

	private Double thatValue;
	private String thatUnit;
	private String thatMeasurementType;

	@Enumerated(EnumType.STRING)
	private OperationType operation;

	private String resultString;
	private Double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	private String errorMessage;
	private boolean isError;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
