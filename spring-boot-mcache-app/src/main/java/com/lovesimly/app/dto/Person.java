package com.lovesimly.app.dto;

import java.io.Serializable;
import java.util.Date;

public class Person  implements Serializable{
	
	

	private String name;
	
	private Integer age;
	
	private Date birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}
	
	
	
	
}
