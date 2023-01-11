package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Orders;

@Repository
public class OrdersDaoImple implements OrdersDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public OrdersDaoImple(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertOrders(Orders orders) {
		jdbcTemplate.update("INSERT INTO orders(customer_id,menu_id,order_time,delivery_flg,pay_flg) VALUES(?, ?, ?,0,0)",
				orders.getCustomerId(),orders.getMenuId(),orders.getOrderTime());
	}

	@Override
	public List<Orders> findAllByCustomerId(int seatId) {
		String sql = "SELECT orders.id as order_id,orders.customer_id,orders.menu_id,orders.order_time,orders.delivery_flg,shopMenu.menu_name,shopMenu.price "
				        + "FROM orders INNER JOIN shopMenu ON orders.menu_id = shopMenu.id WHERE customer_id = ?";
		List<Orders> list = new ArrayList<>();
		List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql,seatId);
		for(Map<String, Object> result : resultList) {
			Orders orders = new Orders();
			orders.setId((int)result.get("order_id"));
			orders.setCustomerId((int)result.get("customer_id"));
			orders.setMenuId((int)result.get("menu_id"));
			orders.setMenuName((String)result.get("menu_name"));
			orders.setPrice((int)result.get("price"));
			orders.setOrderTime(((Timestamp)result.get("order_time")).toLocalDateTime());
			orders.setDeliveryFlg((int)result.get("delivery_flg"));
			list.add(orders);
		}
		return list;
	}

	@Override
	public int updateDeliveryFlg(int ordersId) {
		return jdbcTemplate.update	("UPDATE orders SET delivery_flg = 1 WHERE id = ?",ordersId);
	}

	@Override
	public int deleteAllBySeatId(int seatId) {
		return jdbcTemplate.update("DELETE FROM orders WHERE customer_id = ?", seatId);
	}
	
	
	
	
}
