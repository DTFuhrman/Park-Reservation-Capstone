package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Screen {

	private PrintWriter out;
	private Scanner in;
	
	
	/*
	public Screen(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	
	public Object getChoiceFromOptions(Menu options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object options) {
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

	private void displayMenuOptions(Object options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	//needs a method to get input
	public String getInput() {
		String output = keyboard.nextLine();
		return output;
	}
	
	//needs a method to pause scrolling
	public String pauseScrolling() {
		System.out.println("Press enter to continue");
		String userInput = getInput();
		return userInput;
	}
	
	//needs a method to print menus
	public void printMenu(Object printMe) {
		System.out.println(printMe);
	}
	
	//needs a method to print results
	public boolean printResult(SqlRowSet results) {
		boolean printed = false;
		while (results.next()) {
			System.out.print(results);
			printed = true;
		}
		return printed;
	}
	*/
}
