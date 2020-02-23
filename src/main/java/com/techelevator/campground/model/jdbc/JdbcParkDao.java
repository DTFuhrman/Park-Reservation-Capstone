package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;


public class JdbcParkDao implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcParkDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Park mapFromSQL(int park_id, String name, String location, Date establish_date, double area, int visitors,
			String description) {
		Park thePark = new Park();
		thePark.setPark_id(park_id);
		thePark.setName(name);
		thePark.setLocation(location);
		thePark.setEstablish_date(establish_date);
		thePark.setArea(area);
		thePark.setVisitors(visitors);
		thePark.setDescription(description);

		return thePark;
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<>();
		String sqlGetAllParks = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		
		while (results.next()) {
			Park newPark = mapFromSQL(results.getInt("park_id"), results.getString("name"), results.getString("location"),
					results.getDate("establish_date"), results.getDouble("area"), results.getInt("visitors"),
					results.getString("description"));

			parks.add(newPark);
		}
		return parks;

	}

	@Override
	public List<Park> getAllParksWithUtilityHookups() {
		List<Park> parksWithUtilities = new ArrayList<>();
		String sqlGetAllParksWithUtilities = "SELECT * FROM park JOIN campground ON park.park_id = campground.park_id JOIN site ON campground.campground_id = site.campground_id WHERE site.utilities = true";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParksWithUtilities);
		
		while (results.next()) {
			Park newPark = mapFromSQL(results.getInt("park_id"), results.getString("name"), results.getString("location"),
					results.getDate("establish_date"), results.getDouble("area"), results.getInt("visitors"),
					results.getString("description"));

			parksWithUtilities.add(newPark);
		}
		return parksWithUtilities;
	}


	//Fix this SQL query
	@Override
	public List<Park> getAllParksWithVacancy() {
		List<Park> parksWithVacancy = new ArrayList<>();
		String sqlGetAllParksWithVacancy = "SELECT * FROM park JOIN campground ON park.park_id = campground.park_id JOIN site ON campground.campground_id = site.campground_id WHERE site.utilities = true";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParksWithVacancy);
		
		while (results.next()) {
			Park newPark = mapFromSQL(results.getInt("park_id"), results.getString("name"), results.getString("location"),
					results.getDate("establish_date"), results.getDouble("area"), results.getInt("visitors"),
					results.getString("description"));
			
			parksWithVacancy.add(newPark);
		}
		return null;
	}

}
