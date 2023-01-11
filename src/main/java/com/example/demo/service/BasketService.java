package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Basket;

public interface BasketService {
	
	List<Basket> getAll ();
	
	void insert(Basket basket);
	
	List<Basket> getAllByCustomerId(int customId);
	
	void deleteByMenuid(int menuid);
	
	void deleteAllByCostomerId(int costomId);
}
