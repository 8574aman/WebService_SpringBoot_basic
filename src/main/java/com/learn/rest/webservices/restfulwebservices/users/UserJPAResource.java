package com.learn.rest.webservices.restfulwebservices.users;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.learn.rest.webservices.restfulwebservices.users.exception.UserNotFoundException;
import com.learn.rest.webservices.restfulwebservices.users.post.Post;
import com.learn.rest.webservices.restfulwebservices.users.post.PostRepository;

@RestController
public class UserJPAResource {

	@Autowired
	private userDaoService daoService;
	
	@Autowired
	private userRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/JPA/users")
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	@GetMapping("/JPA/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id)
	{
		Optional<User> user  = userRepository.findById(id);
		if(!user.isPresent()) 
		{
			throw new UserNotFoundException("id-"+id);
		}
		
		// Implement Hateos at this API getUser 1. import static class WebMVCLinkBuilder and use its linkTo() static method to get the links
		// 2. change return type from User to Resource<User> and add the link to resource and return the resource.
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder links = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(links.withRel("get-AllUsers"));
		return resource;    
	}
	// below endpint will send filtered value of user implementing the Mappingjacksonvalue
	@GetMapping("/JPA/usersFiltered/{id}")
	public MappingJacksonValue getUserFiltered(@PathVariable int id)
	{
		User user  = daoService.findOne(id);
		if(user == null)
		{
			throw new UserNotFoundException("id-"+id);
		}
		// use a mapping as below to implement dynamic filtering
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		
		// create a simple filter that only sends name and buirhtdate in response
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","bithDate");
		
		// create filters and then add it to the mapping initiated above. ....// the first argument should be name of Class 
		FilterProvider filters =  new SimpleFilterProvider().addFilter("UserFieldFilter",filter);
		
		mapping.setFilters(filters );
		return mapping;    
	}

	@PostMapping("/JPA/users")
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
	{
		User savedUser = userRepository.saveAndFlush(user);
		
		URI location = ServletUriComponentsBuilder // this returns us the location of the resource created
				       .fromCurrentRequest()       // then we create a response Entity to Respond with a 201 created. 
				       .path("/{id}")
				       .buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/JPA/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		boolean userExists = userRepository.existsById(id);
		if(userExists)
		userRepository.deleteById(id);
		else
		{
			throw new UserNotFoundException("id"+id);
		}
	}
	
	// below rest services are for posts of the users.
	
	@GetMapping("/JPA/users/{id}/posts")
	public List<Post> getAllPostForUser(@PathVariable int id)
	{
		Optional<User> user  = userRepository.findById(id);
		if(!user.isPresent()) 
		{
			throw new UserNotFoundException("id-"+id);
		}

		List<Post> result = user.get().getPosts();
		
		return result;
	}
	
	// add a post for specific user.
	
	@PostMapping("/JPA/users/{id}/posts")
	public ResponseEntity<Object> addPostforUser(@Valid @RequestBody Post post,@PathVariable int id)
	{
		Optional<User> user  = userRepository.findById(id);
		if(!user.isPresent()) 
		{
			throw new UserNotFoundException("id-"+id);
		}
		
		//List<Post> Exisitingpost = user.get().getPosts();
		
		//Exisitingpost.add(post);
		
		post.setUser(user.get());
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder // this returns us the location of the resource created
				       .fromCurrentRequest()       // then we create a response Entity to Respond with a 201 created. 
				       .path("/{id}")
				       .buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
