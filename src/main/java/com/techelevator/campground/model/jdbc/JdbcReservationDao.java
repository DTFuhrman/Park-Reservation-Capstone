package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JdbcReservationDao implements ReservationDAO {

	@Override
	public Reservation mapReservationFromSQL(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date,
			LocalDate create_date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameOnReservation(int reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSiteId(int reservationId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkReservation(Reservation yesNoReservation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reservation makeReservation(Reservation newReservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> getAvailableReservationsByCampGround(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
