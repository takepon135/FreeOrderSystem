package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrdersDao;
import com.example.demo.entity.Orders;

@Service
public class OrdersServicrImpl implements OrdersService {
	
	private final OrdersDao ordersDao;
	
	@Autowired
	public OrdersServicrImpl(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	
	@Override
	public void insertOrders(List<Orders> ordersList) {
		for(Orders orders : ordersList) {
			ordersDao.insertOrders(orders);
		}
	}

	@Override
	public List<Orders> getAllByCustomerId(int customId) {
		List<Orders> list = ordersDao.findAllByCustomerId(customId);
		return list;
	}

	@Override
	public void updateDeliveryFlg(int ordersId) {
		//メニューを更新　idが無ければ例外発生
		if(ordersDao.updateDeliveryFlg(ordersId) == 0) {
			throw new MenuNotFoundException("更新するオーダーIDが存在しません");
				}
			}

	@Override
	public void deleteAllBySeatId(int seatId) {
		//メニューを削除   idが無ければ例外発生
		if(ordersDao.deleteAllBySeatId(seatId)  == 0) {
			throw new MenuNotFoundException("削除するオーダーが存在しません");
		}
	}
		
	}


