package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	//Store private static final menu objects that contain what we need to display in 
	//different menus in similar enough formats that they can be fed to the displayMenu Function

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

	}
}
