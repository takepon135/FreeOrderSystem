package com.example.demo.entity;

import java.time.LocalDateTime;

public class CompleteOrder {
	private int c_id;
	private int c_seat_id;
	private int c_menu_id;
	private LocalDateTime c_create_time;
	private String menuName;
	private int price;
	
	public CompleteOrder() {}
	
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public int getC_seat_id() {
		return c_seat_id;
	}
	public void setC_seat_id(int c_seat_id) {
		this.c_seat_id = c_seat_id;
	}
	public int getC_menu_id() {
		return c_menu_id;
	}
	public void setC_menu_id(int c_menu_id) {
		this.c_menu_id = c_menu_id;
	}
	public LocalDateTime getC_create_time() {
		return c_create_time;
	}
	public void setC_create_time(LocalDateTime c_create_time) {
		this.c_create_time = c_create_time;
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
}
