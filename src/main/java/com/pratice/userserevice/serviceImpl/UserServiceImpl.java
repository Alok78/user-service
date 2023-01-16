package com.pratice.userserevice.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pratice.userserevice.entity.Hotel;
import com.pratice.userserevice.entity.Rating;
import com.pratice.userserevice.entity.User;
import com.pratice.userserevice.exception.ResourceNotFoundException;
import com.pratice.userserevice.proxy.HotelServiceProxy;
import com.pratice.userserevice.repository.UserRepository;
import com.pratice.userserevice.service.UserService;


@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HotelServiceProxy proxy;
	
	private Logger logger=Logger.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String id = UUID.randomUUID().toString();
		user.setUsserId(id);
		return repository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return  repository.findAll();
	}

	@Override
	public User getUser(String userId) {
		//Return a single user from user-service
		User user = repository.findById(userId).orElseThrow(() -> 
		new ResourceNotFoundException("Resource is not found on server with give id :" +userId));
		//fetching rating of the above user from Rating-service
		//http://localhost:8083/ratings/users/b0b649c3-bd11-43f7-84ee-04f9d78d3fd2
		
		Rating[] userRating = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUsserId(), Rating[].class);
		logger.info(userRating);
		List<Rating> ratings = Arrays.stream(userRating).toList();
		List<Rating> ratingList = ratings.stream().map(rating  -> {
		//api call to hotel-service to get the hotel
		//http://localhost:8082/hotels/24bc502b-c7d9-4457-8b8f-911ace1c54a4
			//This is used as restTemplate to fetch hotel data
			/*
			 * ResponseEntity<Hotel> forEntity =
			 * restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),
			 * Hotel.class); Hotel hotel = forEntity.getBody();
			 */
		//Here we use FEIGN To get Hotel data
			
			Hotel hotel = proxy.getHotel(rating.getHotelId());
			//set the hotel to rating
		rating.setHotel(hotel);
				
		return rating;
		}).toList();
		
		user.setRatings(ratingList);
		return user;
	}

}
