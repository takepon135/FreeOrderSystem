package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Basket;

@Repository
public class BasketDaoImple implements BasketDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BasketDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertBasket(Basket basket) {
		jdbcTemplate.update("INSERT INTO basket(customer_id,menu_id,order_time) VALUES(?, ?, ?)",
				basket.getCustomerId(),basket.getMenuId(),basket.getOrderTime());
	}

	@Override
	public List<Basket> findAllByCustomerId(int customId) {
		String sql = "SELECT id,customer_id,menu_id,order_time FROM basket WHERE customer_id = ?";
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql,customId);
		List<Basket> list = new ArrayList<Basket>();
		for(Map<String,Object> result : resultList) {
			Basket basket = new Basket();
			basket.setId((int)result.get("id"));
			basket.setCustomerId((int)result.get("customer_id"));
			basket.setMenuId((int)result.get("menu_id"));
		    basket.setOrderTime(((Timestamp)result.get("order_time")).toLocalDateTime() );
		    list.add(basket);
		}
		return list;
	}

	@Override
	public int deleteAllByCostomerId(int customId) {
		return jdbcTemplate.update("DELETE FROM basket WHERE customer_id = ?", customId);
	}

	@Override
	public int deleteByMenuid(int menuId) {
		return jdbcTemplate.update("DELETE FROM basket WHERE menu_id = ?", menuId);
	}
	//seatIdもキーに設定する必要あり

}
