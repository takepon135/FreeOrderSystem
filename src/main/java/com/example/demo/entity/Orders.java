package com.example.demo.entity;

import java.time.LocalDateTime;

public class Orders {
	private int id;
	private int customerId;
	private int menuId;
	private String menuName;
	private int price;
	private LocalDateTime orderTime;
	private int deliveryFlg;
	private int payFlg;
	
	public Orders() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public LocalDateTime getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDeliveryFlg() {
		return deliveryFlg;
	}

	public void setDeliveryFlg(int deliveryFlg) {
		this.deliveryFlg = deliveryFlg;
	}

	public int getPayFlg() {
		return payFlg;
	}

	public void setPayFlg(int payFlg) {
		this.payFlg = payFlg;
	}
	
}
