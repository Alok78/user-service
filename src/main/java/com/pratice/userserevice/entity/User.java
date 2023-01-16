package com.pratice.userserevice.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity

public class User 
{
	@Id
	@Column(name="ID")
	private String usserId;
	private String name;
	private String email;
	private String about;
	
	@Transient
	private List<Rating> ratings=new ArrayList<>();
}
