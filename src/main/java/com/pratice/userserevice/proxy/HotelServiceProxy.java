package com.pratice.userserevice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pratice.userserevice.entity.Hotel;

@FeignClient(name="hotel-service")
public interface HotelServiceProxy
{
	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable String hotelId);
}
