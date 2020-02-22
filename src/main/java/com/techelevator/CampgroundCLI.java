package com.techelevator;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;
import com.techelevator.campground.model.Site;
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
		this.menu = new Menu(System.in, System.out);

		campgroundDAO = new JdbcCampgroundDao(dataSource);
		reservationDAO = new JdbcReservationDao(dataSource);
		parkDAO = new JdbcParkDao(dataSource);
		siteDAO = new JdbcSiteDao(dataSource);
	}

	public void run() {
		// Start the flow once control flow is done
		mainMenu();
	}

	public void mainMenu() {
		menu.displayBanner("Main Menu");
		while (true) {
			menu.printMenu(MAIN_MENU_OPTIONS);
			String input = acceptMainMenuInput(MAIN_MENU_OPTIONS);
			if (input.equals(MAIN_MENU_VIEW_PARK_DETAILS)) {
				handleParkList();
			}
			if (input.equals(MAIN_MENU_PICK_PARK_FOR_RESERVATION)) {
				handlePickPark();
			}
			if (input.equals(MAIN_MENU_PARK_WIDE_RESERVATION)) {
				handleParkwideReservation();
			}
			if (input.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
			}
		}
	}

	private String acceptMainMenuInput(String[] mainMenuOptions) {
		// TODO Auto-generated method stub
		return null;
	}

	// Menu methods and Handle methods
	public void handleParkList() {

		menu.displayBanner("Park List");
		List<Park> parks = parkDAO.getAllParks();
		while (true) {
			menu.printParkList(parks);
			Park choicePark = acceptParkListInput(parks);
			if (choicePark == null) {
				System.out.println("No Valid Park Found");
				continue;
			} else if (choicePark.toString().equals("Back")) {
				break; 
			} else {
				handleParkDetail(choicePark);
			}
		}
	}

	private Park acceptParkListInput(List<Park> parks) {
		// TODO Auto-generated method stub
		return null;
	}

	private void handleParkDetail(Park choicePark) {
		// TODO Auto-generated method stub

	}

	public void handlePickPark() {
		List<Park> parks = parkDAO.getAllParks();
		menu.displayBanner("Pick a Park");
		while (true) {
			menu.printParkList(parks);
			Park choicePark = acceptPickParkInput(parks);
			if (choicePark == null) {
				System.out.println("<***** Please Enter a Number or Select (Back) *****>");
			}
			if (choicePark.toString().equals("Back")) {
				break;
			}
			handleCampground(choicePark);
		}
	}

	private Park acceptPickParkInput(List<Park> parks) {
		// TODO Auto-generated method stub
		return null;
	}

	public void handleCampground(Park park) {
		String parkName = park.getName();
		menu.displayBanner(parkName + " | Campgrounds");
		List<Campground> campgrounds = campgroundDAO.getAllCampgroundsByPark((int) park.getPark_id());
		while (true) {
			menu.printCampgroundList(campgrounds);
			Campground choiceCampground = acceptCampgroundInput(campgrounds);
			handleReservationByCampground(choiceCampground);
		}
	}

	private Campground acceptCampgroundInput(List<Campground> campgrounds) {
		// TODO Auto-generated method stub
		return null;
	}

	public void handleReservationByCampground(Campground campground) {
		int chosenCampgroundID = campground.getCampground_id();
		while (true) {
			LocalDate[] reservationDates = acceptReservationInput();
			List<Site> reservedSites = siteDAO.getSitesReservedOnDates(chosenCampgroundID, reservationDates[0],
					reservationDates[1]);
			handleSiteList(reservedSites);
			menu.printSiteList(reservedSites);
			Reservation justBooked = checkReservation(chosenCampgroundID, reservationDates[0], reservationDates[1]);
			boolean booked = bookReservation(justBooked);
			handleBookAttempt(booked);
		}
	}

	private LocalDate[] acceptReservationInput() {
		// TODO Auto-generated method stub
		return null;
	}

	private void handleSiteList(List<Site> reservedSites) {

	}

	private Reservation checkReservation(int chosenCampgroundID, LocalDate localDate, LocalDate localDate2) {
		// TODO Auto-generated method stub
		return null;
	}

	private void handleParkwideReservation() {
		// TODO Auto-generated method stub

	}
	
	private boolean bookReservation(Reservation justBooked) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void handleBookAttempt(boolean booked) {
		// TODO Auto-generated method stub
		
	}
}