package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CompleteOrder;

public interface CompleteOrderService {
	
	void insertComOrder(List<CompleteOrder> comOrderList);
	
	List<CompleteOrder> getAllComOrder();
}
