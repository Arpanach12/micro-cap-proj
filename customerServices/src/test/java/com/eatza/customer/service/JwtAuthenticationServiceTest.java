package com.eatza.customer.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.dto.UserLoginDto;
import com.eatza.customer.exception.UnauthorizedException;
import com.eatza.customer.model.User;
import com.eatza.customer.repository.CustomerRegistrationRepository;
import com.eatza.customer.service.authenticationservice.JwtAuthenticationService;





@RunWith(SpringRunner.class)
public class JwtAuthenticationServiceTest {
	

	@InjectMocks
	JwtAuthenticationService jwtAuthenticationService;
	
	@Mock
	private CustomerRegistrationRepository userRepository;
	
	@Test
	public void authenticateUser_Test() throws UnauthorizedException {
		UserLoginDto user = new UserLoginDto();
		user.setPassword("password");
		user.setEmail("email@gmail.com");
		User userobj=new User("name","lastname","email@gmail.com","password");
		when(userRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(userobj);

     	 jwtAuthenticationService.authenticateUser(user);

	}
	
	@Test(expected=UnauthorizedException.class)
	public void authenticateUser_invalidpassword() throws UnauthorizedException {
		UserLoginDto user = new UserLoginDto();
		user.setPassword("invalid");
		user.setEmail("email@gmail.com");
		User userobj=new User("name","lastname","email@gmail.com","password");

		when(userRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(userobj);

         jwtAuthenticationService.authenticateUser(user);
		
	}
	
	@Test(expected=UnauthorizedException.class)
	public void authenticateUser_invalidEmail() throws UnauthorizedException {
		UserLoginDto user = new UserLoginDto();
		user.setPassword("password");
		user.setEmail("invallidEmail");
		User userobj=null;
		when(userRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(userobj);

         jwtAuthenticationService.authenticateUser(user);	
	}
	
	
}