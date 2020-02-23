package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;

public class JdbcCampgroundDao implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcCampgroundDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Campground mapCampgroundFromSQL(int campground_id, int park_id, String name, int open_from_mm, int open_to_mm, double daily_fee) {
		Campground theCampground = new Campground();
		theCampground.setCampground_id(campground_id);
		theCampground.setPark_id(park_id);
		theCampground.setName(name);
		theCampground.setOpen_from_mm(open_from_mm);
		theCampground.setOpen_to_mm(open_to_mm);
		theCampground.setDaily_fee(daily_fee);
		
		return theCampground;
	}

	@Override
	public List<Campground> getAllCampgroundsByPark(int park_id) {
		List<Campground> campgroundsByPark = new ArrayList<>();
		String sqlAllCampgroundsQueary = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllCampgroundsQueary, park_id);
		while (results.next()){
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getDouble("daily_fee"));
			campgroundsByPark.add(eachCampground);
		}
		
		return campgroundsByPark;
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> campgrounds = new ArrayList<>();
		String sqlAllCampgroundsQueary = "SELECT * FROM campground";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllCampgroundsQueary);
		while (results.next()){
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getInt("daily_fee"));
			campgrounds.add(eachCampground);
		}
		
		return campgrounds;
	}

	
	
	//*********************************************
	//*********************************************
	//*************NOT WORKING YET*****************
	//*********************************************
	//*********************************************
	@Override
	public List<Campground> getAllCampgroundsWithVacancy(LocalDate resStart, LocalDate resEnd) {
		//Grab all the reservations between the dates
		//compare the desired reservation dates
		//use a left join to find empty entries where there is no reservation matching the query
		
		
		List<Campground> campgroundsInSeason = new ArrayList<>();
		String sqlCampgroundsSeasonQueary = "SELECT * FROM campground JOIN site ON campground.site_id = site.site_id JOIN reservation ON site.site_id = reservation.site_id WHERE reservation. >= ?::date AND login_date <  ?::date";
		//format start date
		String formattedStartDate = resStart.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		//format end date
		String formattedEndDate = resStart.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundsSeasonQueary, formattedStartDate, formattedEndDate);
		while (results.next()){
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getDouble("daily_fee"));
			campgroundsInSeason.add(eachCampground);
		}
		
		return campgroundsInSeason;
	}

	@Override
	public List<Campground> getAllCampgroundsInSeason(int resStartMonth, int resEndMonth) {
		List<Campground> campgroundsInSeason = new ArrayList<>();
		String sqlCampgroundsSeasonQueary = "SELECT * FROM campground JOIN site ON campground.site_id = site.site_id JOIN reservation ON site.site_id = reservation.site_id WHERE open_from_mm <= ? AND open_to_mm >  ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampgroundsSeasonQueary, resStartMonth, resEndMonth);
		while (results.next()){
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getDouble("daily_fee"));
			campgroundsInSeason.add(eachCampground);
		}
		
		return campgroundsInSeason;
	}

	@Override
	public List<Campground> getAllCampgroundsWithUtilityHookups() {
		List<Campground> campgroundsWithUtil = new ArrayList<>();
		String sqlAllCampgroundsQueary = "SELECT * FROM campground JOIN site ON campground.campground_id = site.campground_id WHERE utilities = TRUE";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllCampgroundsQueary);
		while (results.next()){
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getDouble("daily_fee"));
			campgroundsWithUtil.add(eachCampground);
		}
		
		return campgroundsWithUtil;
	}

}
