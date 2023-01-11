package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CompleteOrderDao;
import com.example.demo.entity.CompleteOrder;

@Service
public class CompleteOrderServiceImpl implements CompleteOrderService {
	
	private final CompleteOrderDao dao;
	
	@Autowired
	public CompleteOrderServiceImpl(CompleteOrderDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void insertComOrder(List<CompleteOrder> comOrderList) {
		for(CompleteOrder comOrder : comOrderList) {
			dao.insertComOrder(comOrder);
		}
	}

	@Override
	public List<CompleteOrder> getAllComOrder() {
		List<CompleteOrder> list = dao.getAllComOrder();
		return list;
	}

}
