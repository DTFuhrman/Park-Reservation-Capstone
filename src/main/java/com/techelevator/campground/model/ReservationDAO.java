package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationDAO {

	public Reservation mapReservationFromSQL(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date, LocalDate create_date);
	
	public String getNameOnReservation(int reservationId);
	
	public int getSiteId(int reservationId);
	
	public boolean checkReservation(Reservation yesNoReservation);
	
	public Reservation makeReservation(Reservation newReservation);
	
	public List<Reservation> getAvailableReservationsByCampGround(Date startDate, Date endDate);

}
