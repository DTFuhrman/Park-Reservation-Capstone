package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationDAO {

	public Reservation mapReservationFromSQL(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date, LocalDate create_date);
	
	public Reservation getReservationById(int reservationId);
	
	public List<Reservation> getReservationsByName(String searchString);
	
	public List<Reservation> getReservationsBySite(int site_id);
	
	public List<Reservation> getReservationsByCreate(LocalDate dateMade);
	
	public List<Reservation> getReservationsByDate(LocalDate date);
	
	public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate);
	
	public boolean checkReservation(Reservation yesNoReservation);
	
	public Reservation makeReservation(Reservation newReservation);
	
	public List<Reservation> getAvailableReservationsByCampGround(int campgroundID, Date startDate, Date endDate);

	public int getNextID();

}
