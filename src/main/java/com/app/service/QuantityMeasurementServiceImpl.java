package com.app.service;

import com.app.dto.QuantityDTO;
import com.app.dto.QuantityInputDTO;
import com.app.dto.QuantityMeasurementDTO;
import com.app.model.*;
import com.app.repository.QuantityMeasurementRepository;
import com.app.core.IMeasurable;
import com.app.core.LengthUnit;
import com.app.core.TemperatureUnit;
import com.app.core.VolumeUnit;
import com.app.core.WeightUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    private IMeasurable getUnitInstance(String type, String unit) {
        if ("LENGTHUNIT".equalsIgnoreCase(type)) return LengthUnit.valueOf(unit.toUpperCase());
        if ("TEMPERATUREUNIT".equalsIgnoreCase(type)) return TemperatureUnit.valueOf(unit.toUpperCase());
        if ("WEIGHTUNIT".equalsIgnoreCase(type)) return WeightUnit.valueOf(unit.toUpperCase());
        if ("VOLUMEUNIT".equalsIgnoreCase(type)) return VolumeUnit.valueOf(unit.toUpperCase());
        throw new IllegalArgumentException("Unsupported Measurement Type: " + type);
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO input) {
        QuantityDTO q1 = input.getThisQuantityDTO();
        QuantityDTO q2 = input.getThatQuantityDTO();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        populateEntityBase(entity, q1, q2, OperationType.COMPARE);

        try {
            if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
                throw new IllegalArgumentException("Cannot compare different measurement types: "
                        + q1.getMeasurementType() + " vs " + q2.getMeasurementType());
            }

            IMeasurable u1 = getUnitInstance(q1.getMeasurementType(), q1.getUnit());
            IMeasurable u2 = getUnitInstance(q2.getMeasurementType(), q2.getUnit());

            boolean result = Double.compare(
                    u1.convertToBaseUnit(q1.getValue()),
                    u2.convertToBaseUnit(q2.getValue())) == 0;

            entity.setResultString(String.valueOf(result));
            entity.setResultValue(0.0);
            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            // FIX: return error DTO — do NOT re-throw so controller gets proper response body
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityInputDTO input) {
        QuantityDTO q1 = input.getThisQuantityDTO();
        QuantityDTO q2 = input.getThatQuantityDTO();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        populateEntityBase(entity, q1, q2, OperationType.ADD);

        try {
            if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
                throw new IllegalArgumentException("Cannot perform arithmetic between different measurement types.");
            }

            IMeasurable u1 = getUnitInstance(q1.getMeasurementType(), q1.getUnit());
            IMeasurable u2 = getUnitInstance(q2.getMeasurementType(), q2.getUnit());

            if (!u1.supportsArithmetic() || !u2.supportsArithmetic()) {
                throw new IllegalArgumentException("Addition is not supported for temperature units.");
            }

            double base1 = u1.convertToBaseUnit(q1.getValue());
            double base2 = u2.convertToBaseUnit(q2.getValue());
            double finalValue = u1.convertFromBaseUnit(base1 + base2);
            finalValue = Math.round(finalValue * 100.0) / 100.0;

            entity.setResultValue(finalValue);
            entity.setResultUnit(q1.getUnit());
            entity.setResultMeasurementType(q1.getMeasurementType());
            entity.setResultString(q1.getValue() + " " + q1.getUnit() + " + "
                    + q2.getValue() + " " + q2.getUnit() + " = " + finalValue + " " + q1.getUnit());
            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            // FIX: return error DTO — do NOT re-throw
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);
        }
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        List<QuantityMeasurementEntity> entities =
                repository.findByOperation(OperationType.valueOf(operation.toUpperCase()));
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    private void populateEntityBase(QuantityMeasurementEntity entity,
                                    QuantityDTO q1, QuantityDTO q2, OperationType type) {
        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());
        entity.setThatValue(q2.getValue());
        entity.setThatUnit(q2.getUnit());
        entity.setThatMeasurementType(q2.getMeasurementType());
        entity.setOperation(type);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {
        QuantityDTO q1 = input.getThisQuantityDTO();
        QuantityDTO q2 = input.getThatQuantityDTO();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        populateEntityBase(entity, q1, q2, OperationType.CONVERT);

        try {
            if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
                throw new IllegalArgumentException("Cannot convert between different measurement types.");
            }

            IMeasurable sourceUnit = getUnitInstance(q1.getMeasurementType(), q1.getUnit());
            IMeasurable targetUnit = getUnitInstance(q2.getMeasurementType(), q2.getUnit());

            double baseValue = sourceUnit.convertToBaseUnit(q1.getValue());
            double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
            convertedValue = Math.round(convertedValue * 100.0) / 100.0;

            entity.setResultValue(convertedValue);
            entity.setResultUnit(q2.getUnit());
            entity.setResultMeasurementType(q1.getMeasurementType());
            entity.setResultString(q1.getValue() + " " + q1.getUnit()
                    + " = " + convertedValue + " " + q2.getUnit());

            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            // FIX: return error DTO — do NOT re-throw (controller checks isError() flag)
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);
        }
    }
}