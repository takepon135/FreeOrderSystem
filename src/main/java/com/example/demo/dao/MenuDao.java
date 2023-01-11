package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Menu;

public interface MenuDao {
	
	void insertMenu(Menu menu);
	
	List<Menu> getAllMenu();
	
	Optional<Menu> findById(int id);
	
	int update(Menu menu);
	
	int deleteByID(int id);
}
