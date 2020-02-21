package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	//Store private static final MENU objects WITH PREWRITTEN STRINGS that contain what we need to display in 
	//different menus in similar enough formats that they can be fed to the displayMenu Function
	private static final String MAIN_MENU_VIEW_PARK_DETAILS = "Read About Our Fantastic Parks";
	private static final String MAIN_MENU_PICK_PARK_FOR_RESERVATION = "Pick A Park To Make A Reservation";
	private static final String MAIN_MENU_PARK_WIDE_RESERVATION = "Make A Reservation At Any Park";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_VIEW_PARK_DETAILS, 
																	 MAIN_MENU_PICK_PARK_FOR_RESERVATION, 
																	 MAIN_MENU_PARK_WIDE_RESERVATION, 
																	 MAIN_MENU_OPTION_EXIT };

	//Declare objects like Menu and JDBC-DAO objects
	//Instantiate them in the constructor
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
	}

	public void run() {
		//Start the flow once control flow is done
		
	}
	
	// Menu methods and Handle methods
	
	
	
	
	
}
