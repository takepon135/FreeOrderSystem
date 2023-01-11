package com.example.demo.app.manage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class MenuForm {
//	@NotNull(message="空白は許可されていません。")
//	private Integer shopId;
	@NotNull(message="空白は許可されていません。")
	@Size(min = 1, max = 50,message = "メニュー名の文字数は1~50文字の範囲です。")
	private String menuName;
	@NotNull(message="空白は許可されていません。")
	@Size(min = 1, max = 100,message = "メニュー詳細の文字数は1~100文字の範囲です。")
	private String detail;
	@NotNull(message="空白は許可されていません。")
	private Integer price;
	
	private Boolean isNewMenu;
	
	public MenuForm() {
	}
//	public Integer getShopId() {
//		return shopId;
//	}
//	public void setShopId(Integer shopId) {
//		this.shopId = shopId;
//	}
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Boolean getIsNewMenu() {
		return isNewMenu;
	}
	public void setIsNewMenu(Boolean isNewMenu) {
		this.isNewMenu = isNewMenu;
	}
	

}