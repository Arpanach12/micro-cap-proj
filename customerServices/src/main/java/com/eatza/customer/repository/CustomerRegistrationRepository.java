package com.eatza.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.customer.model.User;
@Repository
public interface CustomerRegistrationRepository extends JpaRepository<User, Long> {

//	List<User> findAllCustomerByEmail(String email);
	
	User findCustomerByEmail(String email);
}
