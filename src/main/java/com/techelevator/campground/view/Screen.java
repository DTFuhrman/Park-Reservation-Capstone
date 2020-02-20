package com.techelevator.campground.view;

import java.util.Scanner;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Screen {

	
public Scanner keyboard = new Scanner(System.in);


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
}
