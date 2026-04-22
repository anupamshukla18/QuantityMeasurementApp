package com.app;

import com.app.dto.QuantityDTO;
import com.app.dto.QuantityInputDTO;
import com.app.dto.QuantityMeasurementDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuantityMeasurementApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		// Verifies the Spring context boots successfully
	}

	@Test
	public void testFullAdditionFlow_Success() {
		QuantityInputDTO input = new QuantityInputDTO();
		input.setThisQuantityDTO(new QuantityDTO(2.0, "INCHES", "LENGTHUNIT"));
		input.setThatQuantityDTO(new QuantityDTO(2.0, "INCHES", "LENGTHUNIT"));

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity("/api/v1/quantities/add", input,
				QuantityMeasurementDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(4.0, response.getBody().getResultValue());
		assertEquals("add", response.getBody().getOperation());
	}

	@Test
	public void testAdditionFlow_TemperatureError_Returns400() {
		QuantityInputDTO input = new QuantityInputDTO();
		input.setThisQuantityDTO(new QuantityDTO(100.0, "CELSIUS", "TEMPERATUREUNIT"));
		input.setThatQuantityDTO(new QuantityDTO(100.0, "CELSIUS", "TEMPERATUREUNIT"));

		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/quantities/add", input, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Addition not supported"));
	}
}