package com.learn.rest.webservices.restfulwebservices.users;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User {

	private Integer id;

	@Size(min=2,message="Name should be of min. 2 charachters.")
	private String name;
    
    @Past
	private Date bithDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBithDate() {
		return bithDate;
	}
	public void setBithDate(Date bithDate) {
		this.bithDate = bithDate;
	}
	public User(){}
	
	public User(Integer id, String name, Date bithDate) {
		super();
		this.id = id;
		this.name = name;
		this.bithDate = bithDate;
	}

}
