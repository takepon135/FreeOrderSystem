package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Basket;

public interface BasketDao {
	
	void insertBasket(Basket basket);
	
	List<Basket> findAllByCustomerId(int customId);
	
	int deleteAllByCostomerId(int customId);
	
	int deleteByMenuid(int menuId);
	
}
