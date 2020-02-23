package com.techelevator.campground.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Site;
import com.techelevator.projects.model.Department;

public class JdbcParkDaoIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private JdbcParkDao dao;
	private JdbcTemplate jdbcTemplate;
	private String sqlInsertPark;

	@BeforeClass
	public static void setUpDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgress1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JdbcParkDao(dataSource);
		sqlInsertPark = "";

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void checks_map_park_from_sql() {
		int park_id = 42;
		String name = "park";
		String location = "park";
		LocalDate establish_date = LocalDate.ofYearDay(1942, 360);
		double area = 12.25;
		int visitors = 12;
		String description = "yes";

		Park newPark = dao.mapFromSQL(name, location, establish_date, area, visitors, description);
		
		Assert.assertEquals(park_id, newPark.getName());
		Assert.assertEquals(name, newPark.getName());
		Assert.assertEquals(location, newPark.getLocation());
		Assert.assertEquals(establish_date, newPark.getEstablish_date());
		Assert.assertEquals(area, newPark.getArea(), 0);
		Assert.assertEquals(visitors, newPark.getVisitors());
		Assert.assertEquals(description, newPark.getDescription());
	}
	@Test
	public void checks_all_parks() {
		List<Park> allParksBefore = dao.getAllParks();
		int sizeBefore = allParksBefore.size();
		
		jdbcTemplate.update(sqlInsertPark);
		
		List<Park> allParksAfter = dao.getAllParks();
		int sizeAfter = allParksAfter.size();
		
		assertNotNull(allParksAfter);
		assertEquals(sizeBefore + 1, sizeAfter);
	}
	@Test
	public void checks_all_parks_with_utlilities() {
		List<Park> allParksWithUtilityHookups = dao.getAllParksWithUtilityHookups();
		int sizeBefore = allParksWithUtilityHookups.size();

		jdbcTemplate.update(sqlInsertPark);

		List<Park> allParksWithUtilityHookupsAfter = dao.getAllParksWithUtilityHookups();
		int sizeAfter = allParksWithUtilityHookupsAfter.size();

		assertEquals(sizeBefore + 1, sizeAfter);
}
	
	@Test
	public void checks_all_parks_with_vacancy() {
		
	}
}
