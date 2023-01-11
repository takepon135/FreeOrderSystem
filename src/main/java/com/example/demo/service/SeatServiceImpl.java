package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SeatDao;
import com.example.demo.entity.Seat;

@Service
public class SeatServiceImpl implements SeatService {
	
	private final SeatDao dao;
	
	@Autowired
	public SeatServiceImpl(SeatDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Seat> getAll() {
		return dao.getAllSeat();
	}

	@Override
	public void updeteStatus(int id, int status) {
				if(dao.updateStatus(id,status) == 0) {
					throw new MenuNotFoundException("テーブル番号が存在しません");
				}
	}

	@Override
	public Optional<Seat> getSeat(int seatId) {
		try {
			return dao.findSeat(seatId);
		}catch(EmptyResultDataAccessException e){
			throw new MenuNotFoundException("指定の席番号が存在しません。");
		}
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
	}

	@Override
	public void insertSeat(int newQuanitity) {
		for(int i = 1; i <= newQuanitity; i++) {
			Seat seat = new Seat();
			seat.setSeatId(i);
			seat.setStatus(1);
			dao.insertSeat(seat);
		}
	}
	

}
