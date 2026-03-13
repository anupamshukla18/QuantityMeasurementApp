package com.apps.repository;

import java.util.List;
import com.apps.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void saveMeasurement(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();

}