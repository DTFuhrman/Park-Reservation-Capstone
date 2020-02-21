package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JdbcCampgroundDao;
import com.techelevator.campground.model.jdbc.JdbcParkDao;
import com.techelevator.campground.model.jdbc.JdbcReservationDao;
import com.techelevator.campground.model.jdbc.JdbcSiteDao;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	// Store private static final MENU objects WITH PREWRITTEN STRINGS that contain
	// what we need to display in
	// different menus in similar enough formats that they can be fed to the
	// displayMenu Function
	// MAIN MENU
	private static final String MAIN_MENU_VIEW_PARK_DETAILS = "Read About Our Fantastic Parks";
	private static final String MAIN_MENU_PICK_PARK_FOR_RESERVATION = "Pick A Park To Make A Reservation";
	private static final String MAIN_MENU_PARK_WIDE_RESERVATION = "Make A Reservation At Any Park";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_VIEW_PARK_DETAILS,
			MAIN_MENU_PICK_PARK_FOR_RESERVATION, MAIN_MENU_PARK_WIDE_RESERVATION, MAIN_MENU_OPTION_EXIT };

	// CAMPGROUND MENU
	private static final String CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS = "View A List Of Our Campgrounds";
	private static final String CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION = "Search For A Reservation";
	private static final String CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN = "Return To Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS,
			CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION, CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// RESERVATION MENU
	private static final String RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS = "Search For Available Reservations";
	private static final String RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN = "Return To Previous Screen";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {
			RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS, RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// Declare objects like Menu and JDBC-DAO objects
	// Instantiate them in the constructor
	private Menu menu;
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private ParkDAO parkDAO;
	private SiteDAO siteDAO;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource dataSource) {
		// create your DAOs here
		campgroundDAO = new JdbcCampgroundDao(dataSource);
		reservationDAO = new JdbcReservationDao(dataSource);
		parkDAO = new JdbcParkDao(dataSource);
		siteDAO = new JdbcSiteDao(dataSource);
	}

	public void run() {
		//Start the flow once control flow is done
		displayMApplicationBanner();
		printHeading("Main Menu");
		while(true) {
			printMenu(MAIN_MENU_OPTIONS);
			String input = AcceptMenuInput(MAIN_MENU_OPTIONS);
			if input.equals(MAIN_MENU_VIEW_PARK_DETAILS){
				handleParkList();
			} 
			if input.equals(MAIN_MENU_PICK_PARK_FOR_RESERVATION){
				handlePickPark();
			} 
			if input.equals(MAIN_MENU_PARK_WIDE_RESERVATION){
				handleParkwideReservation();
			} 
			if input.equals(MAIN_MENU_OPTION_EXIT){
				System.exit(0);
			}
		}
	}

	// Menu methods and Handle methods
	public void handleParkList() {
		
		List<Park> parks = parkDAO.getAllParks();
		printHeading("Our Beautiful Parks");
		while(true) {
		printParkList(parks);
		String input = AcceptParkListInput(parks);
		int parkNumber = Integer.parseInt(input);
		Park thePark = parks.get(parkNumber);
		handleCampground(thePark);
		if input.equals("back"){
			break;
		}
		}
		
	}
	
	public void handleCampground(Park park) {
		String parkName = park.getName(); //get it from the list above using the number
		
		printHeading(parkName +" Campgrounds:");
		List<Campground> campgrounds = campgroundDAO.getAllCampgroundsByPark((int)park.getPark_id());
		while(true) {
		printCampgroundList(campgrounds);
		String input =  AcceptCampgroundInput(campgrounds);
		
		Campground theCampground = 
	}

}
