package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	private String[] bannerNames = new String[] { "Main Menu", "Pick Park", "Park List", "Campground", "Site" };

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	/*
	 * ************ WE STOLE THIS FROM PROJECTS CLI!!! ***************
	 * 
	 * 
	 * 
	 * public Object getChoiceFromOptions(Object[] options) { Object choice = null;
	 * while(choice == null) { displayMenuOptions(options); choice =
	 * getChoiceFromUserInput(options); } return choice; }
	 * 
	 * 
	 * private Object getChoiceFromUserInput(Object[] options) { Object choice =
	 * null; String userInput = in.nextLine(); try { int selectedOption =
	 * Integer.valueOf(userInput); if(selectedOption <= options.length) { choice =
	 * options[selectedOption - 1]; } } catch(NumberFormatException e) { // eat the
	 * exception, an error message will be displayed below since choice will be null
	 * } if(choice == null) {
	 * out.println("\n*** "+userInput+" is not a valid option ***\n"); } return
	 * choice; }
	 * 
	 * 
	 * 
	 * private void displayMenuOptions(Object[] options) { out.println(); for(int i
	 * = 0; i < options.length; i++) { int optionNum = i+1;
	 * out.println(optionNum+") "+options[i]); }
	 * out.print("\nPlease choose an option >>> "); out.flush(); }
	 * 
	 * 
	 */

	public String getGenericInput() {
		String input = in.nextLine();
		return input;
	}

	public void printMenu(String[] menuOptions) {
		System.out.println("\n");
		for (String str : menuOptions) {
			System.out.println(str);
		}
	}

	public void displayBanner(String bannerName) {
		if (bannerName.equals(bannerNames[0])) {
			System.out.println("National Park Interface Main Menu\n" + "What can we do for you today?\n");
		}

		if (bannerName.equals(bannerNames[1])) {
			System.out.println("Select Park To Book A Reservation\n");
		}

		if (bannerName.equals(bannerNames[2])) {
			System.out.println("Select Park For Further Details\n");
		}

		if (bannerName.contains(bannerNames[3])) {
			String[] array = bannerName.split("\\|");
			String parkName = array[0];
			System.out.println("Welcome to " + parkName + "\nPlease Select a Campground\n");
		}

		if (bannerName.contains(bannerNames[4])) {
			System.out.println("Let's Make a Reservation\n");
		}

	}

	public void printCampgroundList(List<Campground> campgrounds) {

		System.out.println("Search for Campground Reservation");
		System.out.println("=========================================================>");
		System.out.println("     Name           Open      Closed   Daily Fee");

		int i = 1;

		for (Campground camp : campgrounds) {
			String displayLine = i + ".) " + camp.toString() + "\t" + camp.getOpen_from_mm() + "\t"
					+ camp.getOpen_to_mm() + "\t" + camp.getDaily_fee();
			System.out.println(displayLine);
			i++;
		}
		System.out.println("\n" + i + ".) Back\n");
		System.out.println("Which Campground (0 to cancel)");
	}

	public void printParkList(List<Park> parks) {
		int i = 1;
		for (Park park : parks) {
			String displayLine = i + ".) " + park.toString();
			System.out.println(displayLine);
			i++;
		}
		System.out.println("\n" + i + ".) Back\n");
	}

//refactor this if you can figure out how to just get a list of unreserved sites
	public void printSiteListByCampground(List<Site> reservedSites, List<Site> allSites, Campground campground,
			int numOfDays) {

		List<Site> availableSites = new ArrayList<Site>();
		int campgroundId = 0;
		Campground firstCampground = null;

		System.out.println("Results Matching Your Search Criteria");
		System.out.println(
				"=================================================================================================>");
		System.out.println(
				"             Campground  Site No.   Max Occup.   Accessible?   RV Length   Utility?   Cost(In USD)");

		for (Site everySite : allSites) {
			for (Site resSite : reservedSites) {
				if (everySite.getSite_id() == resSite.getSite_id())
					everySite.markUnavailable();
			}
		}

		double costPerDay = campground.getDaily_fee();
		double totalCost = numOfDays * costPerDay;

		for (Site site : allSites) {
			if (!site.isAvailable() && (campground.getCampground_id() == site.getCampground_id())) {
				System.out.println("***BOOKED***" + site.getSite_number() + "\t" + site.getMax_occupancy() + "\t"
						+ site.isAccessible() + "\t" + site.getMax_rv_length() + "\t" + site.isUtilities() + "\t$"
						+ totalCost);
			} else if (site.getCampground_id() == campground.getCampground_id()) {
				System.out.println("**AVAILABLE**" + site.getSite_number() + "\t" + site.getMax_occupancy() + "\t"
						+ site.isAccessible() + "\t" + site.getMax_rv_length() + "\t" + site.isUtilities() + "\t$"
						+ totalCost);
			}
		}
	}

	public void printSiteListByPark(List<Site> reservedSites, List<Campground> campgrounds, List<Site> allSites,
			Park park, int numOfDays) {

		List<Site> availableSites = new ArrayList<Site>();
		int campgroundId = 0;
		Campground firstCampground = null;

		System.out.println("Results Matching Your Search Criteria");
		System.out.println(
				"=================================================================================================>");
		System.out.println(
				"             Campground  Site No.   Max Occup.   Accessible?   RV Length   Utility?   Cost(In USD)");

		for (Site everySite : allSites) {
			for (Site resSite : reservedSites) {
				if (everySite.getSite_id() == resSite.getSite_id())
					everySite.markUnavailable();
			}
		}

		for (Campground theCamp : campgrounds) {

			double costPerDay = theCamp.getDaily_fee();
			double totalCost = numOfDays * costPerDay;

			for (Site site : allSites) {
				if (!site.isAvailable() && (site.getCampground_id() == theCamp.getCampground_id())) {
					System.out.println("***BOOKED***" + site.getSite_number() + "\t" + site.getMax_occupancy() + "\t"
							+ site.isAccessible() + "\t" + site.getMax_rv_length() + "\t" + site.isUtilities() + "\t$"
							+ totalCost);
				} else if (site.getCampground_id() == theCamp.getCampground_id()) {
					System.out.println("**AVAILABLE**" + site.getSite_number() + "\t" + site.getMax_occupancy() + "\t"
							+ site.isAccessible() + "\t" + site.getMax_rv_length() + "\t" + site.isUtilities() + "\t$"
							+ totalCost);
				}
			}
		}

	}

}
