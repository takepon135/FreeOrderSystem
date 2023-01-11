package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Menu;

@Repository
public class MenuDaoImpl implements MenuDao {
	
	private final JdbcTemplate jdbcTemplate; 
	
	@Autowired
	public MenuDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertMenu(Menu menu) {
		jdbcTemplate.update("INSERT INTO shopMenu(menu_name,detail,price) VALUES(?, ?, ? )",
				menu.getMenuName(),menu.getDetail(),menu.getPrice());
	}

	@Override
	public List<Menu> getAllMenu() {
		String sql = "SELECT id ,menu_name, detail, price FROM shopMenu";
		List<Map<String, Object>>  resultList = jdbcTemplate.queryForList(sql);
		List<Menu> list = new ArrayList<Menu>();
		for(Map<String, Object> result : resultList) {
			Menu menu = new Menu();
			menu.setId((int)result.get("id"));
			menu.setMenuName((String)result.get("menu_name"));
			menu.setDetail((String)result.get("detail"));
			menu.setPrice((int)result.get("price"));
			list.add(menu);
		}
		return list;
	}

	@Override
	public Optional<Menu> findById(int id) {
		String sql = "SELECT  id , menu_name, detail, price FROM shopMenu WHERE id = ?";
		//タスクを1取得
		Map<String, Object> result = jdbcTemplate.queryForMap(sql,id);
		Menu menu = new Menu();
		menu.setId((int)result.get("id"));
		menu.setMenuName((String)result.get("menu_name"));
		menu.setDetail((String)result.get("detail"));
		menu.setPrice((int)result.get("price"));
		//Menuをoptionalでラップする。
		Optional<Menu> menuOpt = Optional.ofNullable(menu);
		return menuOpt;
	}
		
	@Override
	public int update(Menu menu){
		return jdbcTemplate.update	("UPDATE shopMenu SET menu_name = ?,detail = ?,price = ? WHERE id = ?",
				menu.getMenuName(),menu.getDetail(),menu.getPrice(),menu.getId());
	}

	@Override
	public int deleteByID(int id) {
		return jdbcTemplate.update("DELETE FROM shopMenu WHERE id = ?", id);
	}

}
