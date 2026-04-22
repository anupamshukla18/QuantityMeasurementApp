package com.app.dto;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

import com.app.model.QuantityMeasurementEntity;

@Data
public class QuantityMeasurementDTO {
	private Double thisValue;
	private String thisUnit;
	private String thisMeasurementType;
	private Double thatValue;
	private String thatUnit;
	private String thatMeasurementType;
	private String operation;
	private String resultString;
	private Double resultValue;
	private String resultUnit;
	private String resultMeasurementType;
	private String errorMessage;
	private boolean error;

	public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
		dto.setThisValue(entity.getThisValue());
		dto.setThisUnit(entity.getThisUnit());
		dto.setThisMeasurementType(entity.getThisMeasurementType());
		dto.setThatValue(entity.getThatValue());
		dto.setThatUnit(entity.getThatUnit());
		dto.setThatMeasurementType(entity.getThatMeasurementType());
		dto.setOperation(entity.getOperation() != null ? entity.getOperation().name().toLowerCase() : null);
		dto.setResultString(entity.getResultString());
		dto.setResultValue(entity.getResultValue());
		dto.setResultUnit(entity.getResultUnit());
		dto.setResultMeasurementType(entity.getResultMeasurementType());
		dto.setErrorMessage(entity.getErrorMessage());
		dto.setError(entity.isError());
		return dto;
	}

	public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
		return entities.stream().map(QuantityMeasurementDTO::fromEntity).collect(Collectors.toList());
	}
}