package com.apps.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityMeasurementEntity {

    private String operation;
    private String operand1;
    private String operand2;
    private String result;
    private String errorMessage;

}
