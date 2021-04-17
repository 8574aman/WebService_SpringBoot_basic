package com.learn.rest.webservices.restfulwebservices.users.post;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.rest.webservices.restfulwebservices.users.User;
@Entity
public class Post {

	@Id
	@GeneratedValue
	Integer id;
	
	String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore // ignoring user beacuse , while fetching List<Post> from user object it would also try to get deatils of user , which in turn of post and a loop !
	User user;
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	public Post()
	{}
	public Post(Integer id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
}
