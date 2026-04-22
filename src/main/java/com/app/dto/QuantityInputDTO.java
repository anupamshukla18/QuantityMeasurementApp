package com.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuantityInputDTO {
	@Valid
	@NotNull(message = "First quantity cannot be null")
	private QuantityDTO thisQuantityDTO;

	@Valid
	@NotNull(message = "Second quantity cannot be null")
	private QuantityDTO thatQuantityDTO;
}