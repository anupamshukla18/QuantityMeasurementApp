package com.apps.service;

import com.apps.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO input, String targetUnit);

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);

}
