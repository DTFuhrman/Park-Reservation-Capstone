package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
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
	public Campground mapCampgroundFromSQL(int campground_id, int park_id, String name, int open_from_mm, int open_to_mm, int daily_fee) {
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
			Campground eachCampground = mapCampgroundFromSQL(results.getInt("campground_id"), results.getInt("park_id"), results.getString("name"), results.getInt("open_from_mm"), results.getInt("open_to_mm"), results.getInt("daily_fee"));
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

	@Override
	public List<Campground> getAllCampgroundsWithVacancy(LocalDate resStart, LocalDate resEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campground> getAllCampgroundsInSeason(LocalDate resStart, LocalDate resEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campground> getAllCampgroundsWithUtilityHookups() {
		// TODO Auto-generated method stub
		return null;
	}

}
