package com.techelevator.campground.model.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
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

import com.techelevator.campground.model.Site;

public class JdbcSiteDaoIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JdbcSiteDao dao;
	private JdbcTemplate jdbcTemplate;
	private String sqlInsertSite;
	private String sqlInsertPark;
	private String sqlInsertCampground;
	private String sqlGetNextIdForPark;
	private String sqlGetNextIdForCampground;

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
		sqlGetNextIdForPark = "SELECT nextval('seq_park_id')";
		sqlGetNextIdForCampground = "SELECT nextval('seq_campground_id')";
		sqlInsertPark = "INSERT INTO park VALUES(?, 'name', 'location', '1984-06-02', 283928, 329294829, 'description')";
		sqlInsertCampground = "INSERT INTO campground VALUES(?, ?, 'name', '02', '04', 34.43)";
		sqlInsertSite = "INSERT INTO site VALUES(DEFAULT, ?, 42, 42, true, 200, true)";

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void checks_map_site_from_sql() {
		// Arrange
		int site_id = 42;
		int campground_id = 32;
		int site_number = 42;
		int max_occupancy = 42;
		boolean accessible = true;
		int max_rv_length = 42;
		boolean utilities = false;

		// Act
		Site newSite = dao.mapSiteFromSQL(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length,
				utilities);

		// Assert
		Assert.assertEquals(site_id, newSite.getSite_id());
		Assert.assertEquals(campground_id, newSite.getCampground_id());
		Assert.assertEquals(site_number, newSite.getSite_number());
		Assert.assertEquals(max_occupancy, newSite.getMax_occupancy());
		Assert.assertEquals(accessible, newSite.isAccessible());
		Assert.assertEquals(max_rv_length, newSite.getMax_rv_length());
		Assert.assertEquals(utilities, newSite.isUtilities());
	}

	@Test
	public void checks_for_campsites_with_utility_hookups() {
		List<Site> allSitesWithUtilityHookups = dao.getAllCampSitesWithUtilityHookups();
		int sizeBefore = allSitesWithUtilityHookups.size();
		SqlRowSet parkIdRowSet = jdbcTemplate.queryForRowSet(sqlGetNextIdForPark);
		int parkId = parkIdRowSet.getInt(0);
		SqlRowSet campgroundIdRowSet = jdbcTemplate.queryForRowSet(sqlGetNextIdForCampground);
		int campgroundId = campgroundIdRowSet.getInt(0);
		jdbcTemplate.update(sqlInsertPark, parkId);
		jdbcTemplate.update(sqlInsertCampground, campgroundId, parkId);
		jdbcTemplate.update(sqlInsertSite, campgroundId);
		

		List<Site> allSitesWithUtilityHookupsAfter = dao.getAllCampSitesWithUtilityHookups();
		int sizeAfter = allSitesWithUtilityHookupsAfter.size();
		

		assertEquals(sizeBefore + 1, sizeAfter);

	}

	@Test
	public void checks_for_rv_accessible_campsites() {
		List<Site> allSitesWithRVAccess = dao.getRVAccessibleCampSites();
		int sizeBefore = allSitesWithRVAccess.size();

		jdbcTemplate.update(sqlInsertSite);

		List<Site> allSitesWithRVAccessAfter = dao.getRVAccessibleCampSites();
		int sizeAfter = allSitesWithRVAccess.size();

		assertEquals(sizeBefore + 1, sizeAfter);

	}

}
