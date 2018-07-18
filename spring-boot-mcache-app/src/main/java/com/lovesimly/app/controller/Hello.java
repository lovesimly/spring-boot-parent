package com.lovesimly.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@RequestMapping("/hello")
	public String index() {
		return "hello wrold";
	}
	
	@RequestMapping("/error")
	public String myError() {
		return "hello wrold error";
	}

}
