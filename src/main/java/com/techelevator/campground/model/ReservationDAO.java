package com.techelevator.campground.model;

import java.util.Date;
import java.util.List;

public interface ReservationDAO {

	public Reservation mapReservationFromSQL(int reservation_id, int site_id, String name, Date from_date, Date to_date, Date create_date);
	
	public String getNameOnReservation(int reservationId);
	
	public int getSiteId(int reservationId);
	
	public boolean checkReservation(Reservation yesNoReservation);
	
	public Reservation makeReservation(Reservation newReservation);
	
	public List<Reservation> getAvailableReservationsByCampGround(Date startDate, Date endDate);

}
