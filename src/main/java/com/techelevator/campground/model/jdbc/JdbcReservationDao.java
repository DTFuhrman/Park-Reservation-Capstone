package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JdbcReservationDao implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcReservationDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Reservation mapReservationFromSQL(int reservation_id, int site_id, String name, LocalDate from_date, LocalDate to_date,
			LocalDate create_date) {
		Reservation theReservation = new Reservation();
		theReservation.setReservation_id(reservation_id);
		theReservation.setSite_id(site_id);
		theReservation.setName(name);
		theReservation.setFrom_date(from_date);
		theReservation.setTo_date(to_date);
		theReservation.setCreate_date(create_date);
		
		return theReservation;
	}

	@Override
	public Reservation getReservationById(int reservationId) {
		
		return null;
	}
	
	@Override
	public List<Reservation> getReservationsByName(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Reservation> getReservationsBySite(int site_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Reservation> getReservationsByCreate(LocalDate dateMade) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Reservation> getReservationsByDate(LocalDate date) {

		
		return null;
	}
	
	@Override
	public List<Reservation> getReservationsByDateRange(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public boolean checkReservation(Reservation yesNoReservation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reservation makeReservation(Reservation newReservation) {
		
		Reservation returnReservation = null;
		//get a new ID
		
		//don't use an ID if you don't know if it's taken *** int res_id = newReservation.getReservation_id();
		int site_id = newReservation.getSite_id();
		String name = newReservation.getName();
		LocalDate from_date = newReservation.getFrom_date();
		LocalDate to_date = newReservation.getTo_date();
		LocalDate create_date = newReservation.getCreate_date();
		
		String sqlInsertNewReservation = "INSERT INTO reservation VALUES ?, ?, ?, ?, ?, ?"; 
		jdbcTemplate.update(sqlInsertNewReservation/*, a, b, c, d, e, f*/);
		
		return returnReservation;
	}

	@Override
	public List<Reservation> getAvailableReservationsByCampGround(int campgroundID, Date startDate, Date endDate) {
		List<Reservation> reservationsByCampground = new ArrayList<>();
		
		//We have to make sure they are avilable by comparing dates
		
		String sqlReservationByCampground = "SELECT * FROM reservation WHERE"; /* NOT WORKING YET */
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReservationByCampground);
		while (results.next()){
			Reservation eachReservation = mapReservationFromSQL(results.getInt("reservation_id"), results.getInt("site_id"), results.getString("name"), results.getDate("from_date").toLocalDate(), results.getDate("to_date").toLocalDate(), results.getDate("create_date").toLocalDate());
			reservationsByCampground.add(eachReservation);
		}
		return reservationsByCampground;
	}

	@Override
	public int getNextID() {
		String sqlNextResID = "SELECT nextval('reservation_reservation_id_seq')";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlNextResID);
		results.next(); // advances to the first row
		int id = results.getInt(1); // returns the integer value of the first column of table (i.e. index 1)
		return id;
	}


}
