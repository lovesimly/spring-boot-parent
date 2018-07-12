package com.lovesimly.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovesimly.app.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	PersonService personService;
	

	
	@RequestMapping("/getperson")
	public String get() {
		 personService.get();
		 return "ok";
	}
	
	
	

}
