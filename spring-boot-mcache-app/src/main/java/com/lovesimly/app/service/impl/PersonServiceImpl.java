package com.lovesimly.app.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.TypeReference;
import com.lovesimly.app.service.PersonService;
import com.lovesimly.mcache.core.MCache;
import com.lovesimly.mcache.core.McacheBuilder;

@Component
public class PersonServiceImpl implements PersonService {
	
	 private MCache<String> mcache;


	  @PostConstruct
	  public void init() throws Exception {
	    mcache =
	        McacheBuilder.newBuilder("channel_head", new TypeReference<String>() {})
	            .expireAfterWrite(10)
	            .maximumSize(1000)
	            .useRemote(100)
	            .build();
	  }
	 
	public void get() {
		System.out.println("get succ");
	}

}
