package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;

@Service
public class MenuServiceImpl implements MenuService {

	private final MenuDao dao;
	
	@Autowired
	public MenuServiceImpl(MenuDao dao) {
		this.dao = dao;		
	}	

	@Override
	public List<Menu> getAll() {
		return dao.getAllMenu();
	}

	@Override
	public Optional<Menu> getMenu(int id) {
		//Optional<Menu>を1件取得 idが無ければ例外発生
		try {
			return dao.findById(id);
		}catch(EmptyResultDataAccessException e){
			throw new MenuNotFoundException("指定されたメニューが存在しません");
		}
	}
	
	@Override
	public void insert(Menu menu) {
		dao.insertMenu(menu);
	}

	@Override
	public void updete(Menu menu) {
		//メニューを更新　idが無ければ例外発生
		if(dao.update(menu) == 0) {
			throw new MenuNotFoundException("更新するメニューが存在しません");
		}
	}

	@Override
	public void deleteById(int id) {
		//メニューを削除 idが無ければ例外発生
		if(dao.deleteByID(id)  == 0) {
			throw new MenuNotFoundException("削除するメニューが存在しません");
		}
		
	}

}
