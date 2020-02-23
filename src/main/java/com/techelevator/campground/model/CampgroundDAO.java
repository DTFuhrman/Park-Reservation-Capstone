package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CampgroundDAO {
	
	public Campground mapCampgroundFromSQL (int campground_id, int park_id, String name, int open_from_mm, int open_to_mm, double daily_fee);
	
	public List<Campground> getAllCampgroundsByPark(int park_id);
	
	public List<Campground> getAllCampgrounds();
	
	public List<Campground> getAllCampgroundsWithVacancy(LocalDate resStart, LocalDate resEnd);
	
	public List<Campground> getAllCampgroundsInSeason(int resStartMonth, int resEndMonth);
	
	public List<Campground> getAllCampgroundsWithUtilityHookups();
	
}
