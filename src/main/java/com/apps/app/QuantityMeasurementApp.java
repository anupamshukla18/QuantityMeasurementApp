package com.apps.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.apps.dto.QuantityDTO;
import com.apps.repository.IQuantityMeasurementRepository;
import com.apps.repository.QuantityMeasurementCacheRepository;
import com.apps.repository.QuantityMeasurementDatabaseRepository;
import com.apps.service.QuantityMeasurementServiceImpl;
import com.apps.util.ApplicationConfig;
import com.apps.util.ConnectionPool;

public class QuantityMeasurementApp {

	public static void main(String[] args) throws SQLException {

		IQuantityMeasurementRepository repository;

		String repositoryType = ApplicationConfig.getProperty("repository.type");

		if ("database".equals(repositoryType)) {

			repository = new QuantityMeasurementDatabaseRepository();

		} else {

			repository = QuantityMeasurementCacheRepository.getInstance();

		}

		// Use dependency injection properly
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repository);

		// Create quantities
		QuantityDTO q1 = new QuantityDTO(1, "FEET");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES");

		// Perform operations
		service.add(q1, q2);
		service.subtract(q1, q2);
		service.divide(q1, q2);

		Connection conn = ConnectionPool.getConnection();
		Statement stmt = conn.createStatement();

		System.out.println("Tables in Database:");

		ResultSet tables = stmt.executeQuery("SHOW TABLES");
		while (tables.next()) {
			System.out.println(tables.getString(1));
		}

		System.out.println("\nStored Measurements:");

		ResultSet rs = stmt.executeQuery("SELECT * FROM QUANTITY_MEASUREMENT_ENTITY");

		while (rs.next()) {
			System.out.println(rs.getLong("id") + " | " + rs.getString("operation") + " | " + rs.getString("operand1")
					+ " | " + rs.getString("operand2") + " | " + rs.getString("result"));
		}
	}
}