package com.techelevator;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

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
	private static final String MAIN_MENU_VIEW_PARK_DETAILS = "1.) Read About Our Fantastic Parks";
	private static final String MAIN_MENU_PICK_PARK_FOR_RESERVATION = "2.) Pick A Park To Make A Reservation";
	private static final String MAIN_MENU_PARK_WIDE_RESERVATION = "3.) Make A Reservation At Any Park";
	private static final String MAIN_MENU_OPTION_EXIT = "4.) Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_VIEW_PARK_DETAILS,
			MAIN_MENU_PICK_PARK_FOR_RESERVATION, MAIN_MENU_PARK_WIDE_RESERVATION, MAIN_MENU_OPTION_EXIT };

	// CAMPGROUND MENU
	private static final String CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS = "1.) View A List Of Our Campgrounds";
	private static final String CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION = "2.) Search For A Reservation";
	private static final String CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN = "3.) Return To Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_VIEW_LIST_OF_CAMPGROUNDS,
			CAMPGROUND_MENU_SEARCH_FOR_A_RESERVATION, CAMPGROUND_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// RESERVATION MENU
	private static final String RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS = "1.) Search For Available Reservations";
	private static final String RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN = "2.) Return To Previous Screen";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {
			RESERVATION_MENU_SEARCH_FOR_AVAILABLE_RESERVATIONS, RESERVATION_MENU_RETURN_TO_PREVIOUS_SCREEN };

	// DETAIL MENU
	private static final String VIEW_CAMPGROUNDS = "1.) View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "2.) Search For Reservation";
	private static final String RETURN_TO_LIST = "3.) Return To Viewing Parks";
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
			} else if (choicePark.getName().equals("Back")) {
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
			returnPark = new Park();
			returnPark.setName("Back");
			return returnPark;
		}
		try {
			choice = Integer.valueOf(input);
		} catch (NumberFormatException e) {
		}
		if (choice == null || (choice < 0 || choice > parks.size() + 1)) {
			return null;
		} else if (choice == parks.size() + 1) {
			returnPark = new Park();
			returnPark.setName("Back");
			return returnPark;
		} else /* ((choice > 0) && (choice < parks.size() + 1)) */ {
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
		handleDescriptionOptions(choicePark);

	}

	private void handleDescriptionOptions(Park park) {
		while (true) {
			menu.printMenu(DETAIL_MENU_OPTIONS);
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
				handleCampground(park);
			} else if (choice == 2) {
				handleReservationByPark(park);
			} else if (choice == 3) {
				break;
			}
		}
	}

	public void handlePickPark() {
		List<Park> parks = parkDAO.getAllParks();
		menu.displayBanner("Pick a Park");
		while (true) {
			menu.printParkList(parks);
			Park choicePark = acceptParkListInput(parks);
			if (choicePark == null) {
				System.out.println("<***** Please Enter a Number or Select (Quit) *****>");
			}
			if (choicePark.toString().equals("Back")) {
				break;
			}
			handleCampground(choicePark);
		}
	}

	public void handleCampground(Park park) {
		String parkName = park.getName();
		menu.displayBanner(parkName + " | Campgrounds");
		List<Campground> campgrounds = campgroundDAO.getAllCampgroundsByPark(park.getPark_id());
		while (true) {
			menu.printCampgroundList(campgrounds);
			Campground choiceCampground = acceptCampgroundInput(campgrounds);
			handleReservationByCampground(choiceCampground);
		}
	}

	private Campground acceptCampgroundInput(List<Campground> campgrounds) {
		String campgroundInput = menu.getGenericInput();
		Integer choice = null;
		Campground returnCampground = null;

		if (campgroundInput.equalsIgnoreCase("q")) {
			returnCampground = new Campground();
			returnCampground.setName("Back");
			return returnCampground;
		}
		try {
			choice = Integer.valueOf(campgroundInput);
		} catch (NumberFormatException e) {
		}
		if (choice == null || (choice < 0 || choice > campgrounds.size())) {
			return null;
		} else if (choice == campgrounds.size() + 1) {
			returnCampground = new Campground();
			returnCampground.setName("Back");
			return returnCampground;
		} else /* ((choice > 0) && (choice < parks.size())) */ {
			choice--;
			returnCampground = campgrounds.get(choice);
			return returnCampground;
		}
	}

	private void handleReservationByPark(Park park) {
		int chosenParkID = park.getPark_id();
		while (true) {
			menu.printMenu(RESERVATION_MENU_OPTIONS);
			LocalDate[] reservationDates = acceptReservationDateInput();
			List<Site> reservedSites = siteDAO.getSitesReservedOnDatesByPark(chosenParkID, reservationDates[0],
					reservationDates[1]);
			List<Site> allSites = siteDAO.getAllSitesByPark(chosenParkID);
			List<Campground> campgrounds = campgroundDAO.getAllCampgroundsByPark(chosenParkID);
			int numOfDays = reservationDates[0].until(reservationDates[1]).getDays();
			numOfDays++;
			menu.printSiteListByPark(reservedSites, campgrounds, allSites, park, numOfDays);
			Reservation justBooked = acceptReservationInput(reservationDates, allSites);
			boolean booked = bookReservation(justBooked);
			if (booked == true){
				break;
			}
		}
	}

	public void handleReservationByCampground(Campground campground) {
		int chosenCampgroundID = campground.getCampground_id();
		while (true) {
			LocalDate[] reservationDates = acceptReservationDateInput();
			List<Site> reservedSites = siteDAO.getSitesReservedOnDates(chosenCampgroundID, reservationDates[0],
					reservationDates[1]);
			List<Site> allSites = siteDAO.getAllSitesByCampground(chosenCampgroundID);
			int numOfDays = reservationDates[0].until(reservationDates[1]).getDays();
			numOfDays++;
			menu.printSiteListByCampground(reservedSites, allSites, campground, numOfDays);
			Reservation justBooked = acceptReservationInput(reservationDates, allSites);
			boolean booked = bookReservation(justBooked);
			if (booked == true){
				break;
			}
		}
	}

	private Reservation acceptReservationInput(LocalDate[] reservationDates,
			List<Site> allSites) {
		Reservation tryToBook = new Reservation();
		Site siteToBook = null;
		// Get next ID for reservations
		int resID = reservationDAO.getNextID();
		int siteID;
		// Let them pick a site and pull the ID
		while (true) {
			System.out.println("Which Site?");
			String siteChosen = menu.getGenericInput();
			int siteNum;
			try {
				siteNum = Integer.valueOf(siteChosen);
			} catch (Exception e) {
				System.out.println("Please pick a valid site");
			}
			siteNum = -1;
			if ((siteNum > 0 && siteNum < allSites.size() + 1) && (!allSites.get(siteNum - 1).isAvailable())) {
				System.out.println("Unavailable. \nYou Must Pick An Available Site");
				continue;
			}
			if ((siteNum > 0 && siteNum < allSites.size() + 1)) {
				siteToBook = allSites.get(siteNum - 1);
				siteID = siteToBook.getSite_id();
				break;
			}
		}
		String resName = acceptReservationNameInput();
		LocalDate fromDate = reservationDates[0];
		LocalDate toDate = reservationDates[1];
		LocalDate createDate = LocalDate.now();

		reservationDAO.mapReservationFromSQL(resID, siteID, resName, fromDate, toDate, createDate);

		return tryToBook;
	}

	private String acceptReservationNameInput() {
		System.out.println("Enter A Name For Your Reservation:");

		String input = menu.getGenericInput();

		return input;
	}

	private LocalDate[] acceptReservationDateInput() {
		LocalDate[] reservationDates = new LocalDate[2];
		// print prompt and collect arrival date
		boolean acceptingInput = false;
		do {
			System.out.println("What is the arrival date? dd/mm/year");

			String firstDateString = menu.getGenericInput();
			LocalDate firstAttempt = null;
			try {
				String[] firstDate = firstDateString.split("/");
				firstAttempt = LocalDate.of(Integer.valueOf(firstDate[2]), Integer.valueOf(firstDate[1]),
						Integer.valueOf(firstDate[0]));
			} catch (Exception e) {
				System.out.println("Date not recognized. Use format dd/mm/year.");
				acceptingInput = true;
			}
			reservationDates[0] = firstAttempt;
		} while (acceptingInput);
		acceptingInput = false;
		do {
			System.out.println("What is the departure date? dd/mm/year");

			String secondDateString = menu.getGenericInput();
			LocalDate secondAttempt = null;
			try {
				String[] secondDate = secondDateString.split("/");
				secondAttempt = LocalDate.of(Integer.valueOf(secondDate[2]), Integer.valueOf(secondDate[1]),
						Integer.valueOf(secondDate[0]));
			} catch (Exception e) {
				System.out.println("Date not recognized. Use format dd/mm/year.");
				acceptingInput = true;
			}

			reservationDates[1] = secondAttempt;
		} while (acceptingInput);
		// print prompt and collect end date
		return reservationDates;
	}

	private void handleParkwideReservation() {
		// TODO Auto-generated method stubs

	}

	private boolean bookReservation(Reservation justBooked) {
		// TODO Auto-generated method stub
		return false;
	}

}