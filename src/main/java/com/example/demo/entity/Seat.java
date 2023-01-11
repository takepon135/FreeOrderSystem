package com.example.demo.entity;

public class Seat {
	
	private int seatId;
	private int status;  //1：未使用　2：使用中 3：会計依頼中 4：要あとかたづけ
	
	public Seat() {}
	
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
