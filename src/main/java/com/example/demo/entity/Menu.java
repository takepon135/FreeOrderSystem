package com.example.demo.entity;

public class Menu {
	private int id;
//	private int shopId;
	private String menuName;
	private String detail;
	private int price;
	
	public Menu() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

//	public int getShopId() {
//		return shopId;
//	}
//
//	public void setShopId(int shopId) {
//		this.shopId = shopId;
//	}

		
}
