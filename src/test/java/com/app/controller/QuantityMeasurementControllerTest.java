package com.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.config.SecurityConfig;
import com.app.dto.QuantityDTO;
import com.app.dto.QuantityInputDTO;
import com.app.dto.QuantityMeasurementDTO;
import com.app.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@Import(SecurityConfig.class)
public class QuantityMeasurementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IQuantityMeasurementService service;

	private QuantityInputDTO validInput;
	private QuantityMeasurementDTO mockResponse;

	@BeforeEach
	void setUp() {
		validInput = new QuantityInputDTO();
		validInput.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LENGTHUNIT"));
		validInput.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LENGTHUNIT"));

		mockResponse = new QuantityMeasurementDTO();
		mockResponse.setResultString("true");
		mockResponse.setOperation("compare");
		mockResponse.setError(false);
	}

	@Test
	public void givenValidInput_whenCompareQuantities_thenReturns200AndResult() throws Exception {
		Mockito.when(service.compare(any(QuantityInputDTO.class))).thenReturn(mockResponse);

		mockMvc.perform(post("/api/v1/quantities/compare").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validInput))).andExpect(status().isOk())
				.andExpect(jsonPath("$.operation").value("compare")).andExpect(jsonPath("$.resultString").value("true"))
				.andExpect(jsonPath("$.error").value(false));
	}

	@Test
	public void givenInvalidInput_whenCompareQuantities_thenReturns400() throws Exception {
		QuantityInputDTO invalidInput = new QuantityInputDTO();
		// Missing the nested DTOs to trigger @Valid constraints

		mockMvc.perform(post("/api/v1/quantities/compare").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidInput))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Validation Error"));
	}
}