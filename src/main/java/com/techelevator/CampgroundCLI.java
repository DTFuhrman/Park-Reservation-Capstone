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
	private static final String MAIN_MENU_VIEW_PARK_DETAILS = "1.) Read About Our Fantastic Parks\n";
	private static final String MAIN_MENU_PICK_PARK_FOR_RESERVATION = "2.) Pick A Park To Make A Reservation\n";
	private static final String MAIN_MENU_PARK_WIDE_RESERVATION = "3.) Make A Reservation At Any Park\n";
	private static final String MAIN_MENU_OPTION_EXIT = "4.) Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_VIEW_PARK_DETAILS,
			MAIN_MENU_PICK_PARK_FOR_RESERVATION, MAIN_MENU_PARK_WIDE_RESERVATION, MAIN_MENU_OPTION_EXIT };

	// CAMPGROUND MENU
	private static final String CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS = "1.) View A List Of Our Campgrounds\n";
	private static final String CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION = "2.) Search For A Reservation\n";
	private static final String CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN = "3.) Return To Previous Screen\n";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS,
			CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION, CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// RESERVATION MENU
	private static final String RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS = "1.) Search For Available Reservations\n";
	private static final String RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN = "2.) Return To Previous Screen\n";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {
			RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS, RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// DETAIL MENU
	private static final String VIEW_CAMPGROUNDS = "1.) View Campgrounds\n";
	private static final String SEARCH_FOR_RESERVATION = "2.) Search For Reservation\n";
	private static final String RETURN_TO_LIST = "3.) Return To Viewing Parks\n";
	private static final String[] DETAIL_MENU_OPTIONS = new String[] { VIEW_CAMPGROUNDS, SEARCH_FOR_RESERVATION,
			RETURN_TO_LIST };

	// Make a series of arrays containing the menus

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
			Integer input = acceptMainMenuInput();
			if (input == 1) {
				handleParkList();
			}
			if (input == 2) {
				handlePickPark();
			}
			if (input == 3) {
				handleParkwideReservation();
			}
			if (input == 4) {
				System.exit(0);
			}
			System.out.println("Please Make A Valid Selection\n");
		}
	}

	private Integer acceptMainMenuInput() {
		while (true) {
			String input = menu.getGenericInput();
			Integer choice = null;

			try {
				choice = Integer.valueOf(input);
			} catch (NumberFormatException e) {
			}
			return choice;
		}
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
		String input = menu.getGenericInput();
		Integer choice = null;

		Park returnPark = null;

		if (input.equalsIgnoreCase("q")) {
			returnPark.setName("Back");
			return returnPark;
		}
		try {
			choice = Integer.valueOf(input);
		} catch (NumberFormatException e) {
		}
		if (choice == null || (choice < 0 || choice > parks.size())) {
			return null;
		} else if (choice == parks.size()) {
			returnPark.setName("Back");
			return returnPark;
		} else /* ((choice > 0) && (choice < parks.size())) */ {
			choice--;
			returnPark = parks.get(choice);
			return returnPark;
		}

	}

	private void handleParkDetail(Park choicePark) {
		System.out.println("Park Info");
		System.out.println("==========================");
		System.out.println(choicePark);
		System.out.println("Location: " + choicePark.getLocation());
		System.out.println("Established: " + choicePark.getEstablish_date());
		System.out.println("Area: " + choicePark.getArea());
		System.out.println("Annual Visitors: " + choicePark.getVisitors() + "\n");
		System.out.println(choicePark.getDescription() + "\n");
		System.out.println("Select a Command");
		handleDescriptionOptions();

	}

	private void handleDescriptionOptions() {
		menu.printMenu(DETAIL_MENU_OPTIONS);
		while (true) {
			String input = menu.getGenericInput();
			Integer choice = null;
			try {
				choice = Integer.valueOf(input);
			} catch (NumberFormatException e) {
			}
			if (choice == null) {
				System.out.println("Please enter a valid option");
				continue;
			} else if (choice < 0 || choice > 3) {
				System.out.println("Please enter a valid option");
				continue;
			} else if (choice == 1) {
				//handleCampground(park);
			} else if (choice == 2) {
				//handleReservationByPark(campground);
			} else if (choice == 3) {

			}
		}
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
			List<Site> allSites = siteDAO.getAllSitesByCampground(chosenCampgroundID);
			int numOfDays = reservationDates[0].until(reservationDates[1]).getDays();
			numOfDays++;
			menu.printSiteListByCampground(reservedSites, allSites, campground, numOfDays);
			Reservation justBooked = checkReservation(chosenCampgroundID, reservationDates[0], reservationDates[1]);
			boolean booked = bookReservation(justBooked);
			handleBookAttempt(booked);
		}
	}

	private LocalDate[] acceptReservationInput() {
		// TODO Auto-generated method stub
		return null;
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