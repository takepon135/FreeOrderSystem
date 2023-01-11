package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.CompleteOrder;

public interface CompleteOrderDao {
	
	public void insertComOrder(CompleteOrder completeOrder);
	
	public List<CompleteOrder> getAllComOrder();
}
