package com.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityInputDTO {

    @NotNull(message = "thisQuantityDTO cannot be null")
    @Valid
    private QuantityDTO thisQuantityDTO;

    @NotNull(message = "thatQuantityDTO cannot be null")
    @Valid
    private QuantityDTO thatQuantityDTO;

    // Optional target unit for convert / add / subtract operations
    private String targetUnit;

}
