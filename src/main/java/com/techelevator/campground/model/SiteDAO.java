package com.techelevator.campground.model;

import java.util.List;

public interface SiteDAO {

	public Site mapSiteFromSQL(int site_id, int campground_id, int site_number, int max_occupancy, boolean accessible, int max_rv_length, boolean utilities);
	
	public List<Campground> getAllCampSitesWithUtilityHookups();
	
	public List<Campground> getAllHandicapAccessibleCampSites();
	
	public List<Campground> getRVAccessibleCampSites();
	
}
