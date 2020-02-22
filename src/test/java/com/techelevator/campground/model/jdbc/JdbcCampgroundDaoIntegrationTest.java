package com.techelevator.campground.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Site;

public class JdbcCampgroundDaoIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private JdbcSiteDao dao;
	private JdbcTemplate jdbcTemplate;
	private String sqlInsertSite;

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
		dao = new JdbcSiteDao(dataSource);
		sqlInsertSite = "";

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test 
	public void checks_map_campground_from_sql() {
		int campground_id = campground;
		int park_id = parkId;
		String name = name;
		int open_from_mm = openFrom;
		int open_to_mm = openTo;
		int daily_fee = dailyFee;
		
		Campground newCampground = dao.mapCampgroundFromSQL(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee);
		
		Assert.assertEquals(campground_id, newCampground.getCampground_id());
		Assert.assertEquals(park_id, newCampground.getPark_id());
		Assert.assertEquals(name, newCampground.getName());
		Assert.assertEquals(open_from_mm, newCampground.getOpen_from_mm());
		Assert.assertEquals(open_to_mm, newCampground.getOpen_to_mm());
		Assert.assertEquals(daily_fee, newCampground.getDaily_fee());
		}
	@Test
	public void checks_all_campgrounds_by_park() {
		jdbcTemplate.update(sqlInsertCampground1);
		jdbcTemplate.update(sqlInsertCampground2);
		
		List<Campground> campgroundByPark = dao.getAllCampgroundsByParkId(Long.valueOf(3));
		assertNotNull(campgroundByPark);
		assertEquals(2, campgroundByPark.size());
		
		Campground actualCampground = campgroundByPark.get(0);
		
		Campground expectedCampground = makeCampground(4, 4, "Toobs Yoob", LocalDate.parse("2020-01-03"), LocalDate.parse("2020-02-18"), 4);
		expectedCampground.setPark_id(actualCampground.getPark_id());
		

		
		assertCampgroundsAreEqual(expectedCampground, actualCampground);
	}
	@Test
	public void checks_all_campgrounds() {
		List<Campground> allCampgroundsBefore = dao.getAllCampgrounds();
		int sizeBefore = allCampgroundsBefore.size();
		
		jdbcTemplate.update(sqlInsertCampground1);
		
		List<Campground> allCampgroundsAfter = dao.getAllCampgrounds();
		int sizeAfter = allCampgroundsAfter.size();
		
		assertNotNull(allCampgroundsAfter);
		assertEquals(sizeBefore + 1, sizeAfter);
	}
	@Test
	public void checks_all_campgrounds_with_vacancy() {
		
	}
	@Test
	public void checks_all_campgrounds_in_season() {
		
	}
	@Test
	public void checks_all_campgrounds_with_utility_hookups() {
		List<Campground> allCampgroundsWithUtilityHookups = dao.getAllCampgroundsWithUtilityHookups();
		int sizeBefore = allCampgroundsWithUtilityHookups.size();

		jdbcTemplate.update(sqlInsertSite);

		List<Site> allCampgroundsWithUtilityHookupsAfter = dao.getAllCampgroundsWithUtilityHookups();
		int sizeAfter = allCampgroundsWithUtilityHookupsAfter.size();

		assertEquals(sizeBefore + 1, sizeAfter);
	}
}
