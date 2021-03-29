package com.learn.rest.webservices.restfulwebservices.users;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learn.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;

@RestController
public class UserResource {

	@Autowired
	private userDaoService daoService;

	@GetMapping("/users")
	public List<User> getAllUsers()
	{
		return daoService.findall();
	}
	@GetMapping("/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id)
	{
		User user  = daoService.findOne(id);
		if(user == null)
		{
			throw new UserNotFoundException("id-"+id);
		}
		
		// Implement Hateos at this API getUser 1. import static class ControllerLinkBuilder and use its linkTo() static method to get the links
		// 2. change return type from User to Resource<User> and add the link to resource and return the resource.
		EntityModel<User> resource = EntityModel.of(user);
		WebMvcLinkBuilder links = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(links.withRel("get-AllUsers"));
		
		return resource;    
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
	{
		User savedUser = daoService.save(user);
		URI location = ServletUriComponentsBuilder // this returns us the location of the resource created
				       .fromCurrentRequest()       // then we create a response Entity to Respond with a 201 created. 
				       .path("/{id}")
				       .buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		User deletedUser = daoService.deleteUser(id);
		if(deletedUser == null)
		{
			throw new UserNotFoundException("id"+id);
		}
	}
}
