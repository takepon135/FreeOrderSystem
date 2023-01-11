package com.example.demo.service;

public class MenuNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MenuNotFoundException(String message) {
		super(message);
	}
}
