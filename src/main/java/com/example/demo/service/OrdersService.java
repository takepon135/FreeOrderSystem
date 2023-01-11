package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Orders;

public interface OrdersService {
	
	void insertOrders(List<Orders> ordersList);
	
	List<Orders> getAllByCustomerId(int customId);
	
	public void updateDeliveryFlg(int ordersId);
	
	public void deleteAllBySeatId(int seatId); 
}
