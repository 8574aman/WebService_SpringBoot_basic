package com.learn.rest.webservices.restfulwebservices.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<User,Integer>{
	
	

}
