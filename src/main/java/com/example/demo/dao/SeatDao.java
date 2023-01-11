package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Seat;

public interface SeatDao {
	
	List<Seat> getAllSeat();
	
	int updateStatus(int id, int status);
	
	Optional<Seat> findSeat(int seatId);
	
	int deleteAll();
	
	void insertSeat(Seat seat);
	
}
