package com.lovesimly.app.service;

import com.lovesimly.app.dto.Person;

public interface McacheService {
	
	public Person get(String key);
	
	public void set(String key,Person person);

}
