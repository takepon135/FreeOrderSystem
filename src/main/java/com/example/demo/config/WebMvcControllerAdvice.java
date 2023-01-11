package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.service.MenuNotFoundException;

@ControllerAdvice
public class WebMvcControllerAdvice {
	
	/*送信された空文字をnullに変換する*/
	@InitBinder
	public void initBinder(WebDataBinder  dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	/*全コントローラーに対し、特定のexceptionに対する処理を実装する。*/
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String handleException(EmptyResultDataAccessException e, Model model) {
		model.addAttribute("message" , e);
		return "error/CustomPage";
	}
	
	@ExceptionHandler(MenuNotFoundException.class)
	public String handoleMenuNotFound(MenuNotFoundException e, Model model) {
		model.addAttribute("message",e);
		return "error/CustomPage";
	}
	

	
	
}
