package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface ParkDAO {
	
	public Park mapFromSQL(String name, String location, LocalDate establish_date, double area, int visitors, String description);
	
	public List<Park> getAllParks();
	
	public List<Park> getAllParksWithUtilityHookups();
	
	public List<Park> getAllParksWithVacancy();
	
	
	
	
	
}
