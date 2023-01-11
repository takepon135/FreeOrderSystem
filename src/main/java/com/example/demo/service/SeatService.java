package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Seat;

public interface SeatService {
	
	List<Seat> getAll ();
	
	void updeteStatus(int id, int status);
	
	Optional<Seat> getSeat(int seatId);
	
	void deleteAll();
	
	void insertSeat(int newQuanitity);
}
