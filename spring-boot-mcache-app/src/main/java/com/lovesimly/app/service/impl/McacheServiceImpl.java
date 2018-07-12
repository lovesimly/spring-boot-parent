package com.lovesimly.app.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.TypeReference;
import com.lovesimly.app.dto.Person;
import com.lovesimly.app.service.McacheService;
import com.lovesimly.mcache.core.MCache;
import com.lovesimly.mcache.core.McacheBuilder;

@Component
public class McacheServiceImpl implements McacheService {
	
	 private MCache<Person> mcache;


	  @PostConstruct
	  public void init() throws Exception {
	    mcache =
	        McacheBuilder.newBuilder("love_test", new TypeReference<Person>() {})
	            .expireAfterWrite(10)
	            .maximumSize(1000)
	            .useRemote(60)//s
	            .build();
	  }
	 


	public Person get(String key) {
		Person person = null;
		try {
			person = (Person)mcache.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

	public void set(String key, Person person) {
		try {
			mcache.set(key, person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
