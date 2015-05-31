package com.telarix.Procedures;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;


public class DBStoredProcedures extends StoredProcedure {

	public DBStoredProcedures(JdbcTemplate jdbcTemplate, String name) {

		super(jdbcTemplate, name);
		setFunction(false);

	}

}