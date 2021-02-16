package com.eatza.customer.service.userregistrationservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.exception.UserException;
import com.eatza.customer.model.User;
import com.eatza.customer.repository.CustomerRegistrationRepository;

@Service
public class userregistrationserviceImpl implements UserRegistrationService {

	
	private static final Logger logger = LoggerFactory.getLogger(userregistrationserviceImpl.class);

	@Autowired
	CustomerRegistrationRepository customerRegistrationRepository;
	public User registeruser(UserDto userdto) throws UserException{
		User userobj = customerRegistrationRepository.findCustomerByEmail(userdto.getEmail());

		if (userobj != null) {
			throw new UserException("user already present");
		}

		logger.debug("In register customer method, creating user object to persist");
		User user = new User(userdto.getFirstName(), userdto.getLastName(), userdto.getEmail(), userdto.getPassword());
		logger.debug("saving user in db");
		User saveuser = customerRegistrationRepository.save(user);
		return saveuser;
	}
	
	@Override
	public User getCustomerByCustomerId(Long customerId) throws UserException {
		logger.debug("getting user from db");

		Optional<User> userobj = customerRegistrationRepository.findById(customerId);
		if (!userobj.isPresent()) {
			throw new UserException("user not present");
		}

		return userobj.get();
	}
	@Override
	public UserDto getCustomerByEmail(String email) throws UserException {
		logger.debug("getting user from db");

		User user=customerRegistrationRepository.findCustomerByEmail(email);
		if(user==null)
		{
			 throw new UserException("user not present");
		}
		logger.debug("userdto");

		UserDto userdto=new UserDto(user.getEmail(),user.getFirstName(),user.getLastName(),user.getPassword());
		return userdto;
	}
	@Override
	public User updateCustomerByCustomerId(Long customerId,UserDto userdto) {
		logger.debug("getting user from db");
		User user=customerRegistrationRepository.findById(customerId).get();
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        User saveduser=customerRegistrationRepository.save(user);
		return saveduser;
	}

	

}
