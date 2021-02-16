package com.eatza.customer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.exception.UserException;
import com.eatza.customer.model.User;
import com.eatza.customer.repository.CustomerRegistrationRepository;
import com.eatza.customer.service.userregistrationservice.userregistrationserviceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserRegistrationServiceTest {

	@InjectMocks
	private userregistrationserviceImpl userregistrationservice;

	@Mock
	private CustomerRegistrationRepository userRepository;
	
	@Test
    public void registeruser_Test() throws UserException{
		List<User> userlist=new ArrayList<User>();

		UserDto userdto=new UserDto("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
//		User user = new User(userdto.getFirstName(),userdto.getLastName(),userdto.getEmail(),userdto.getPassword());

		when(userRepository.save(any(User.class)))
		.thenReturn(new User(userdto.getFirstName(),userdto.getLastName(),userdto.getEmail(),userdto.getPassword()));			
		
		User userobj = userregistrationservice.registeruser(userdto);
		assertNotNull(userobj);
	}
	
	@Test(expected=UserException.class)
    public void registeruser_Failed() throws UserException{

		UserDto userdto=new UserDto("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		User user=new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		when(userRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(user);
		userregistrationservice.registeruser(userdto);
	}
	@Test
	public void getCustomerByCustomerId_Test() throws UserException {

		Optional<User> user = Optional.of(new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef"));
		when(userRepository.findById(anyLong())).thenReturn(user);

		User userobj = userregistrationservice.getCustomerByCustomerId(1L);
		assertEquals("arpana",user.get().getFirstName());
	}

	@Test(expected=UserException.class)
	public void getCustomerByCustomerId_Failed() throws UserException {

		Optional<User> user = Optional.empty();
		when(userRepository.findById(anyLong())).thenReturn(user);
		userregistrationservice.getCustomerByCustomerId(1L);
		
	}
	
	@Test
	public void getCustomerByCustomerEmail_Test() throws UserException {

		User user =new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef");
		when(userRepository.findCustomerByEmail("arpanakumari.ara@gmail.com")).thenReturn(user);

		UserDto userdto = userregistrationservice.getCustomerByEmail("arpanakumari.ara@gmail.com");
		assertEquals("arpana",user.getFirstName());
	}
	
	@Test(expected=UserException.class)
	public void getCustomerByCustomerEmail_Failed() throws UserException {

		User user =new User();
		user=null;
		when(userRepository.findCustomerByEmail("arpanakumari.ara@gmail.com")).thenReturn(user);

		userregistrationservice.getCustomerByEmail("arpanakumari.ara@gmail.com");
	}
	
	@Test
	public void updateCustomerByCustomerId_Test() throws UserException {
      UserDto userdto=new UserDto("ab","cd","ab.ara@gmail.com","adef");
		Optional<User> userobj=Optional.of(new User("arpana","kumari","arpanakumari.ara@gmail.com","abcdef"));
  		when(userRepository.findById(1L)).thenReturn(userobj);
		User user = userregistrationservice.updateCustomerByCustomerId(1L, userdto);
		userobj.get().setEmail(userdto.getEmail());
		userobj.get().setFirstName(userdto.getFirstName());
		userobj.get().setLastName(userdto.getLastName());
		userobj.get().setPassword(userdto.getPassword());
		when(userRepository.save(userobj.get())).thenReturn(userobj.get());
		User saveduser = userregistrationservice.updateCustomerByCustomerId(1L, userdto);

		assertEquals("ab",saveduser.getFirstName());
	}
	
	
}
