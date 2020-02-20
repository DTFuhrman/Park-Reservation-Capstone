package com.techelevator.campground.model;


import java.util.Date;

public class Campground {
private int campground_id;
private int park_id;
private String name;
private Date open_from_mm;
private Date open_to_mm;
private int daily_fee;



//GETTERS AND SETTERS!!!!!!!!!!!!!
public int getCampground_id() {
	return campground_id;
}
public void setCampground_id(int campground_id) {
	this.campground_id = campground_id;
}
public int getPark_id() {
	return park_id;
}
public void setPark_id(int park_id) {
	this.park_id = park_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Date getOpen_from_mm() {
	return open_from_mm;
}
public void setOpen_from_mm(Date open_from_mm) {
	this.open_from_mm = open_from_mm;
}
public Date getOpen_to_mm() {
	return open_to_mm;
}
public void setOpen_to_mm(Date open_to_mm) {
	this.open_to_mm = open_to_mm;
}
public int getDaily_fee() {
	return daily_fee;
}
public void setDaily_fee(int daily_fee) {
	this.daily_fee = daily_fee;
}




}