package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestBox {

	public static void main(String[] args) {
		
		LocalDate printToSee = LocalDate.now();
		String formattedStartDate = printToSee.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		String formattedEndDate = printToSee.format(DateTimeFormatter.ofPattern("yy-dd-MM"));
		System.out.println(printToSee);


		System.out.println("         ___   __    ___  ");
		System.out.println("|\\    | |   | |  \\  |   |  ");
		System.out.println("| \\   | |___| |   | |___/  ");
		System.out.println("|  \\  | |     |   | |   \\  ");
		System.out.println("|   \\ | |     |   | |   |  ");
		System.out.println("|    \\| |     |__/  |__/  ");
		System.out.println("                         ");
		System.out.println("                         ");
	
	

	System.out.println("National Park Interface Main Menu\n");

	String[] bannerNames = new String[] {
			"Main Menu", "Pick Park", "Park List", "Campground" 
	};
	
	String bannerName = "Jelly Stone | Campground";

	if (bannerName.contains(bannerNames[3])) {
		String[] array = bannerName.split("\\|");
		String parkName = array[0];
		System.out.println("Welcome to " + parkName + "\nPlease Select a Campground");
	}
	
	}
}
