package com.pratice.userserevice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratice.userserevice.entity.User;

public interface UserRepository extends JpaRepository<User, String>
{
	
}
