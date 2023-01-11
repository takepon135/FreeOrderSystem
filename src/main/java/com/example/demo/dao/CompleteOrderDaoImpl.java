package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CompleteOrder;

@Repository
public class CompleteOrderDaoImpl implements CompleteOrderDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CompleteOrderDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertComOrder(CompleteOrder completeOrder) {
		jdbcTemplate.update("INSERT INTO completeOrder(c_seat_id, c_menu_id, c_create_time) VALUES(?, ?, ?)",
		completeOrder.getC_seat_id(), completeOrder.getC_menu_id(), completeOrder.getC_create_time());	
	}

	@Override
	public List<CompleteOrder> getAllComOrder() {
		String sql = "SELECT c_id , c_seat_id, c_menu_id, c_create_time, shopMenu.menu_name, shopMenu.price "
				+ "FROM completeOrder INNER JOIN shopMenu ON completeOrder.c_menu_id = shopMenu.id";
		List<Map<String, Object>>  resultList = jdbcTemplate.queryForList(sql);
		List<CompleteOrder> list = new ArrayList<>();
		for(Map<String, Object> result : resultList) {
			CompleteOrder completeOrder = new CompleteOrder();
			completeOrder.setC_id((int)result.get("c_id"));
			completeOrder.setC_seat_id((int)result.get("c_seat_id"));
			completeOrder.setC_menu_id((int)result.get("c_menu_id"));
			completeOrder.setC_create_time(((Timestamp)result.get("c_create_time")).toLocalDateTime());
			completeOrder.setMenuName((String)result.get("menu_name"));
			completeOrder.setPrice((int)result.get("price"));
			list.add(completeOrder);
		}
		return list;
	}
}