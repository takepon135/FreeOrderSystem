package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Menu;

public interface MenuService {
	
	List<Menu> getAll ();
	
	Optional<Menu> getMenu(int id);
	
	void insert(Menu menu);
	
	void updete(Menu menu);
	
	void deleteById(int id);
}
