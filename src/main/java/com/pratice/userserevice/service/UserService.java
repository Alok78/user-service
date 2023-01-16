package com.pratice.userserevice.service;

import java.util.List;

import com.pratice.userserevice.entity.User;

public interface UserService 
{
	User saveUser(User user);
	List<User> getAllUser();
	User getUser(String userId);
}
