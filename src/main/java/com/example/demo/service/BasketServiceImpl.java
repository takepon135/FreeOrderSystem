package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BasketDao;
import com.example.demo.entity.Basket;

@Service
public class BasketServiceImpl implements BasketService {
	
	private final BasketDao dao;
	
	@Autowired
	public BasketServiceImpl(BasketDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Basket> getAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void insert(Basket basket) {
		dao.insertBasket(basket);

	}

	@Override
	public List<Basket> getAllByCustomerId(int customId) {
		List<Basket> list = dao.findAllByCustomerId(customId);
		return list;
	}

	@Override
	public void deleteByMenuid(int menuid) {
		//メニューを削除   idが無ければ例外発生
				if(dao.deleteByMenuid(menuid)  == 0) {
					throw new MenuNotFoundException("削除するメニューが存在しません");
				}
	}

	@Override
	public void deleteAllByCostomerId(int costomId) {
		//メニューを削除   idが無ければ例外発生
		if(dao.deleteAllByCostomerId(costomId) == 0) {
			throw new MenuNotFoundException("削除するメニューが存在しません");
		}
		
	}

}
