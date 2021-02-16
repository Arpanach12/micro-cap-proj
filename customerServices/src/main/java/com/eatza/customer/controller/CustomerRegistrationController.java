package com.eatza.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.exception.UserException;
import com.eatza.customer.model.User;
import com.eatza.customer.service.userregistrationservice.UserRegistrationService;



@RestController
public class CustomerRegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerRegistrationController.class);

	@Autowired
	UserRegistrationService userRegistrationService;
	
	@PostMapping("/register")
	public  ResponseEntity<User> registerCustomer( @RequestHeader String authorization,@RequestBody UserDto userdto) throws UserException{
		logger.debug("In register customer method, calling the service");
		User user = userRegistrationService.registeruser(userdto);
		if(user!=null) {

		logger.debug("registered Successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
		}
		else {
			logger.debug("Unsuccessful attempt");
			throw new UserException("Registration failed");
		}
		

	}
	
	@GetMapping("/register/{customerId}")
	public ResponseEntity<User> customerById(@RequestHeader String authorization, @PathVariable Long customerId) throws UserException{
		logger.debug("In get customer by customerId method, calling service to get customer by ID");
		User userobj = userRegistrationService.getCustomerByCustomerId(customerId);
		if(userobj!=null) {
			logger.debug("Got user Details from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(userobj);
		}
		else {
			logger.debug("No user were found");
			throw new UserException("No result found for specified inputs");
		}
	}
	
	@PutMapping("/register/{customerId}")
	public ResponseEntity<User> updatecustomerById(@RequestHeader String authorization, @PathVariable Long customerId,@RequestBody UserDto userdto) throws UserException{
		logger.debug("In update customer by customerId method, calling service to get customer by ID");
		User userobj = userRegistrationService.updateCustomerByCustomerId(customerId,userdto);
		if(userobj!=null) {
			logger.debug("Got user Details from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(userobj);
		}
		else {
			logger.debug("No user were found");
			throw new UserException("No result found for specified inputs");
		}
	}
	
	@GetMapping("/getcustomer")
	public ResponseEntity<UserDto> customerByEmail(@RequestParam (name="email") String email) throws UserException{
		logger.debug("In get customer by email method, calling service to get customer by email");
		UserDto userobj = userRegistrationService.getCustomerByEmail(email);
		if(userobj!=null) {
			logger.debug("Got user Details from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(userobj);
		}
		else {
			logger.debug("No user were found");
			throw new UserException("No result found for specified inputs");
		}
	}
	
}
