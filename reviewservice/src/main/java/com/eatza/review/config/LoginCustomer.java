package com.eatza.review.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eatza.review.dto.UserDto;


@FeignClient(url="http://localhost:8089/customerservice/",name="arpana")
public interface LoginCustomer {

	@GetMapping("/getcustomer")
	public ResponseEntity<UserDto> customerByEmail(@RequestParam (name="email") String email);
	
}
