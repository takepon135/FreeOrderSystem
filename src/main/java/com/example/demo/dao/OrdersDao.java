package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Orders;

public interface OrdersDao {

	void insertOrders(Orders orders);
	
	List<Orders> findAllByCustomerId(int customId);
	
	public int updateDeliveryFlg(int ordersId);
	
	public int deleteAllBySeatId(int seatId);
}
