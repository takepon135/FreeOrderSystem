package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Seat;

@Repository
public class SeatDaoImpl implements SeatDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SeatDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Seat> getAllSeat() {
		String sql = "SELECT seat_id , status FROM seat";
		List<Map<String, Object>>  resultList = jdbcTemplate.queryForList(sql);
		List<Seat> list = new ArrayList<>();
		for(Map<String,Object> result : resultList) {
			Seat seat = new Seat();
			seat.setSeatId((int)result.get("seat_id"));
			seat.setStatus((int)result.get("status"));
			list.add(seat);
		}
		return list;
	}

	@Override
	public int updateStatus(int id, int status) {
		return jdbcTemplate.update	("UPDATE seat SET status = ? WHERE seat_id = ?",status,id);
	}

	@Override
	public Optional<Seat> findSeat(int seatId) {
		String sql = "SELECT  seat_id, status FROM seat WHERE seat_id = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sql,seatId);
		Seat seat = new Seat();
		seat.setSeatId((int)result.get("seat_id"));
		seat.setStatus((int)result.get("status"));
		Optional<Seat> seatOptional = Optional.ofNullable(seat);	
		return seatOptional;
	}

	@Override
	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM seat");
	}

	@Override
	public void insertSeat(Seat seat) {
		jdbcTemplate.update("INSERT INTO seat(seat_id,status) VALUES(?, ? )",seat.getSeatId(),seat.getStatus());
	}
	
}
