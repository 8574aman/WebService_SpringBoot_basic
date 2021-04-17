package com.learn.rest.webservices.restfulwebservices.users;

import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.learn.rest.webservices.restfulwebservices.users.post.Post;

@Entity
//@JsonFilter("UserFieldFilter")
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@Size(min=2,message="Name should be of min. 2 charachters.")
	private String name;
    
    @Past
	private Date bithDate;
	
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Post> posts;
    
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
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
