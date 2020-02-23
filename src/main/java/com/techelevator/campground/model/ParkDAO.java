package com.techelevator.campground.model;

import java.util.Date;
import java.util.List;


public interface ParkDAO {
	
	public Park mapFromSQL(int park_id, String name, String location, Date establish_date, double area, int visitors, String description);
	
	public List<Park> getAllParks();
	
	public List<Park> getAllParksWithUtilityHookups();
	
	public List<Park> getAllParksWithVacancy();
	
	
	
	
	
}
