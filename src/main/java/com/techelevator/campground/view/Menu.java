package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	private Map<String, Object> menuItems = new HashMap<>();
	
	public Menu(Map<String, Object> items) {
		this.menuItems.putAll(items);
	}

	
}
