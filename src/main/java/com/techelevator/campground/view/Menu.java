package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Site;

public class Menu {

	private PrintWriter out;
	private Scanner in;


	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}


	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}


	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}

	
	
	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	public String getGenericInput() {
		String input = in.nextLine();
		return input;
	}

	
	public void printMenu(String[] mainMenuOptions) {
		// TODO Auto-generated method stub
		
	}

	public void displayBanner(String string) {
		// TODO Auto-generated method stub
		
	}

	public void printCampgroundList(List<Campground> campgrounds) {
		// TODO Auto-generated method stub
		
	}


	public void printParkList(List<Park> parks) {
		// TODO Auto-generated method stub
		
	}


	public void printSiteList(List<Site> reservedSites) {
		// TODO Auto-generated method stub
		
	}
	
}
