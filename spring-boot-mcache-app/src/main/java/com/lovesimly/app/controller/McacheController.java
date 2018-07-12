package com.lovesimly.app.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovesimly.app.dto.Person;
import com.lovesimly.app.service.McacheService;

@RestController
public class McacheController {
	Logger log=LoggerFactory.getLogger(McacheController.class);
	
	@Autowired
	McacheService mcacheService;
	

	
	@RequestMapping("/mcache")
	public String get() {
		String key=Math.random()+"";
		Person person=new Person();
		person.setAge(10);
		person.setBirthday(new Date());
		person.setName("ljz");
		
		mcacheService.set(key, person);
		
		Person result=	mcacheService.get(key);
		log.info(person.toString());
		log.info(result.toString());
		
		
		 return "ok "+(result.getBirthday().equals(person.getBirthday()));
	}
	
	
	

}
