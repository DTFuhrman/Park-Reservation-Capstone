package com.techelevator.campground.view;

import java.util.HashMap;
import java.util.Map;

public class Menu {

	private Map<String, Object> menuItems = new HashMap<>();
	
	public Menu(Map<String, Object> items) {
		this.menuItems.putAll(items);
	} 
	
}
