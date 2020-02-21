package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JbdcSiteDao implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JbdcSiteDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Site mapSiteFromSQL(int site_id, int campground_id, int site_number, int max_occupancy, boolean accessible,
			int max_rv_length, boolean utilities) {
		Site theSite = new Site();
		theSite.setSite_id(site_id);
		theSite.setCampground_id(campground_id);
		theSite.setSite_number(site_number);
		theSite.setMax_occupancy(max_occupancy);
		theSite.setAccessible(accessible);
		theSite.setMax_rv_length(max_rv_length);
		theSite.setUtilities(utilities);

		return theSite;
	}

	
	@Override
	public List<Site> getAllCampSitesWithUtilityHookups() {
		List<Site> campsitesWithUtilities = new ArrayList<>();
		String sqlGetAllCampsitesWithUtilities = "SELECT * FROM park JOIN campground ON park.park_id = campground.park_id JOIN site ON campground.campground_id = site.campground_id WHERE site.utilities = true";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampsitesWithUtilities);

		while (results.next()) {
			Site newCampsite = mapSiteFromSQL(results.getInt("site_id"), results.getInt("campground_id"),
					results.getInt("site_number"), results.getInt("max_occupancy"), results.getBoolean("accessible"),
					results.getInt("max_rv_length"), results.getBoolean("utilities"));

			campsitesWithUtilities.add(newCampsite);
		}

		return campsitesWithUtilities;
	}

	@Override
	public List<Site> getAllHandicapAccessibleCampSites() {

		List<Site> handicapAccessibleCampsites = new ArrayList<>();
		String sqlGetAllHandicapAccessibleCampsites = "SELECT * FROM park JOIN campground ON park.park_id = campground.park_id JOIN site ON campground.campground_id = site.campground_id WHERE site.accessible = true";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllHandicapAccessibleCampsites);

		while (results.next()) {
			Site newCampsite = mapSiteFromSQL(results.getInt("site_id"), results.getInt("campground_id"),
					results.getInt("site_number"), results.getInt("max_occupancy"), results.getBoolean("accessible"),
					results.getInt("max_rv_length"), results.getBoolean("utilities"));

			handicapAccessibleCampsites.add(newCampsite);
		}

		return handicapAccessibleCampsites;
	}

	@Override
	public List<Site> getRVAccessibleCampSites() {

		List<Site> RVAccessibleCampsites = new ArrayList<>();
		String sqlGetAllRVAccessibleCampsites = "SELECT * FROM park JOIN campground ON park.park_id = campground.park_id JOIN site ON campground.campground_id = site.campground_id WHERE site.max_rv_length > 0";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllRVAccessibleCampsites);

		while (results.next()) {
			Site newCampsite = mapSiteFromSQL(results.getInt("site_id"), results.getInt("campground_id"),
					results.getInt("site_number"), results.getInt("max_occupancy"), results.getBoolean("accessible"),
					results.getInt("max_rv_length"), results.getBoolean("utilities"));

			RVAccessibleCampsites.add(newCampsite);
		}

		return RVAccessibleCampsites;
	}

	@Override
	public List<Site> getSitesReservedOnDates(int campground_id, LocalDate start, LocalDate end) {

		List<Site> UnavailableCampsites = new ArrayList<>();
		
		String sqlGetUnavailableCampsites = "SELECT * FROM site LEFT JOIN reservation ON site.site_id = reservation.site_id WHERE (campground_id = ?) AND (daterange(from_date, to_date, '[]') && daterange(date ?, date ?, '[]')) AND from_date IS NOT NULL";
		String formattedStartDate = start.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		String formattedEndDate = end.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetUnavailableCampsites, campground_id, formattedStartDate, formattedEndDate);

		while (results.next()) {
			Site newCampsite = mapSiteFromSQL(results.getInt("site_id"), results.getInt("campground_id"),
					results.getInt("site_number"), results.getInt("max_occupancy"), results.getBoolean("accessible"),
					results.getInt("max_rv_length"), results.getBoolean("utilities"));

			UnavailableCampsites.add(newCampsite);
		}

		return UnavailableCampsites;
	}
}
