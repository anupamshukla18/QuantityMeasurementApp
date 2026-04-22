package com.app.service;

import com.app.dto.QuantityInputDTO;
import com.app.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {
	QuantityMeasurementDTO compare(QuantityInputDTO input);

	QuantityMeasurementDTO add(QuantityInputDTO input);
	QuantityMeasurementDTO convert(QuantityInputDTO input);
	List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
}