package com.eatza.customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.customer.CustomerserviceApplication;
import com.eatza.customer.dto.UserDto;
import com.eatza.customer.model.User;
import com.eatza.customer.service.userregistrationservice.userregistrationserviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@ContextConfiguration(classes= CustomerserviceApplication.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value= CustomerRegistrationController.class)
public class CustomerRegistrationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private userregistrationserviceImpl userregistrationservice;

	@Autowired
	private ObjectMapper objectMapper;
	
	private static final long EXPIRATIONTIME = 900000;
	String jwt="";
	String invalidjwt="";
	
	@Before
	public void setup() {
		jwt = "Bearer "+Jwts.builder().setSubject("user").claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();
	
	
	}
	
	@Test
	public void registerCustomer_Test() throws Exception {
		UserDto userdto = new UserDto();
		User user = new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		userdto.setFirstName("arpana");
		userdto.setLastName("kumari");
		userdto.setEmail("arpanakumari.ara@gmail.com");
		userdto.setPassword("abcdef");

		when(userregistrationservice.registeruser(any(UserDto.class))).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((userdto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
	}
	@Test
	public void registerCustomer_Failed() throws Exception {
		UserDto userdto = new UserDto();
		User user = new User();
		user=null;
		userdto.setFirstName("arpana");
		userdto.setLastName("kumari");
		userdto.setEmail("arpanakumari.ara@gmail.com");
		userdto.setPassword("abcdef");

		when(userregistrationservice.registeruser(any(UserDto.class))).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((userdto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}

	@Test
	public void customerById_Test() throws Exception {
		User user = new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		when(userregistrationservice.getCustomerByCustomerId(anyLong())).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/register/1")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
		
	}
	
	@Test
	public void customerById_Failed() throws Exception {
		User user = new User();
		user=null;
		when(userregistrationservice.getCustomerByCustomerId(anyLong())).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/register/1")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
		
	}
	
	@Test
	public void customerByEmail_Test() throws Exception {
		UserDto userdto =new UserDto("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		when(userregistrationservice.getCustomerByEmail("arpanakumari.ara@gmail.com")).thenReturn(userdto);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/getcustomer").param("email", "arpanakumari.ara@gmail.com")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
		
	}
	@Test
	public void customerByEmail_Failed() throws Exception {
		UserDto userdto =new UserDto();
		userdto=null;
		when(userregistrationservice.getCustomerByEmail("arpanakumari.ara@gmail.com")).thenReturn(userdto);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/getcustomer").param("email", "arpanakumari.ara@gmail.com")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
		
	}
	
	@Test
	public void updatecustomerById_Test() throws Exception {
		UserDto userdto = new UserDto();
		User user = new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		userdto.setFirstName("arpana");
		userdto.setLastName("kumari");
		userdto.setEmail("arpanakumari.ara@gmail.com");
		userdto.setPassword("abcdef");

		when(userregistrationservice.updateCustomerByCustomerId(anyLong(),any(UserDto.class))).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/register/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((userdto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
		
	}
	
	@Test
	public void updatecustomerById_Failed() throws Exception {
		UserDto userdto = new UserDto();
		User user = new User();
		user=null;
		userdto.setFirstName("arpana");
		userdto.setLastName("kumari");
		userdto.setEmail("arpanakumari.ara@gmail.com");
		userdto.setPassword("abcdef");

		when(userregistrationservice.updateCustomerByCustomerId(anyLong(),any(UserDto.class))).thenReturn(user);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/register/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((userdto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
		
	}

}
