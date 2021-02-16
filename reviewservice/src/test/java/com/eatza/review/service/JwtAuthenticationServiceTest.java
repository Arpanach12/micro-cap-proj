//package com.eatza.review.service;
//
//import static org.junit.Assert.assertNotNull;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.eatza.review.dto.UserDto;
//import com.eatza.review.exception.UnauthorizedException;
//import com.eatza.review.service.authenticationservice.JwtAuthenticationService;
//
//
//
//@RunWith(SpringRunner.class)
//public class JwtAuthenticationServiceTest {
//	
//
//	@InjectMocks
//	JwtAuthenticationService jwtAuthenticationService;
//	
//	@Before
//	public void setup() {
//		org.springframework.test.util.ReflectionTestUtils.setField(jwtAuthenticationService, "email", "email@gmail.com");
//		org.springframework.test.util.ReflectionTestUtils.setField(jwtAuthenticationService, "password", "password");
//	}
//	@Test(expected=UnauthorizedException.class)
//	public void authenticateUser_invalidname() throws UnauthorizedException {
//		UserDto user = new UserDto();
//		user.setPassword("password");
//		user.setEmail("email@gmail.com");
//		String jwt= jwtAuthenticationService.authenticateUser(user);
//
//	}
//	
//	@Test(expected=UnauthorizedException.class)
//	public void authenticateUser_invalidpassword() throws UnauthorizedException {
//		UserDto user = new UserDto();
//		user.setPassword("invalid");
//		user.setEmail("email@gmail.com");
//		String jwt= jwtAuthenticationService.authenticateUser(user);
//
//		
//	}
//	
//	@Test
//	public void authenticateUser() throws UnauthorizedException {
//		UserDto user = new UserDto();
//		user.setFirstName("abdc");
//		user.setLastName("efgh");
//		user.setPassword("password");
//		user.setEmail("email@gmail.com");
//		String jwt= jwtAuthenticationService.authenticateUser(user);
//		assertNotNull(jwt);
//	}
//
//	
//
//}
