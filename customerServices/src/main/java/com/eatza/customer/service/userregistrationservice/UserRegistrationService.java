package com.eatza.customer.service.userregistrationservice;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.exception.UserException;
import com.eatza.customer.model.User;

public interface UserRegistrationService {

	User registeruser(UserDto userdto) throws UserException;

	User getCustomerByCustomerId(Long customerId) throws UserException;

	UserDto getCustomerByEmail(String email) throws UserException;

	User updateCustomerByCustomerId(Long customerId, UserDto userdto);

}
