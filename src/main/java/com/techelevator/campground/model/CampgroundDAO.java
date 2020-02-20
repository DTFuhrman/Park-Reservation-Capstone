package com.techelevator.campground.model;

import java.util.List;

public interface CampgroundDAO {
	
	public Campground mapCampgroundFromSQL (int park_id, String name, int open_from_mm, int open_to_mm, int daily_fee);
	
	public List<Campground> getAllCampgroundsByPark();
	
	public List<Campground> getAllCampgrounds();
	
	public List<Campground> getAllCampgroundsWithVacancy();
	
	public List<Campground> getAllCampgroundsInSeason();
	
	public List<Campground> getAllCampgroundsWithUtilityHookups();
	
}
