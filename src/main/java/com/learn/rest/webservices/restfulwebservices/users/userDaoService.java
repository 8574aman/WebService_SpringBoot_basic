package com.learn.rest.webservices.restfulwebservices.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class userDaoService {
private static List<User> users = new ArrayList<User>();
private static int count = 3;
static
{
	users.add(new User(1,"ade",new Date()));
	users.add(new User(2,"eve",new Date()));
	users.add(new User(3,"dale",new Date()));
		
	
}

public List<User> findall()
{
	return users;
}

public User save(User user)
{
	if(user.getId()==null)
	{
		user.setId(++count);
	}
	
	users.add(user);
	
	return user;
}

public User findOne(Integer id)
{
  for(User user : users)
  {
	  if(user.getId() == id)
	  {
		  return user;
	  }
  }
  return null;
}

public User deleteUser(Integer id)
{
	Iterator<User> iterator = users.iterator();
	
	while(iterator.hasNext())
	{
		User user = iterator.next();
		if(user.getId() == id)
		{
			iterator.remove();
			return user;
		}
	}
	return null;
}



}
