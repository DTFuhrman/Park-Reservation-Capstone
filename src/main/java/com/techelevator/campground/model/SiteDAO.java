package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	public Site mapSiteFromSQL(int site_id, int campground_id, int site_number, int max_occupancy, boolean accessible, int max_rv_length, boolean utilities);
	
	public List<Site> getAllCampSitesWithUtilityHookups();
	
	public List<Site> getAllHandicapAccessibleCampSites();
	
	public List<Site> getRVAccessibleCampSites();
	
	public List<Site> getSitesReservedOnDates(int campground_id, LocalDate start, LocalDate end);

	public List<Site> getAllSitesByCampground(int campground_id);

	public List<Site> getAllSitesByPark(int park_id);

	public List<Site> getAllSites();

	public List<Site> getSitesReservedOnDatesByPark(int chosenParkID, LocalDate localDate, LocalDate localDate2);
	
}
