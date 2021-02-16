package com.eatza.customer.service.authenticationservice;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eatza.customer.dto.UserDto;
import com.eatza.customer.dto.UserLoginDto;
import com.eatza.customer.exception.UnauthorizedException;
import com.eatza.customer.model.User;
import com.eatza.customer.repository.CustomerRegistrationRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtAuthenticationService {

	@Autowired
	CustomerRegistrationRepository customerRegistrationRepository;
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);

	@Value("${user}")
	String username;

	@Value("${password}")
	String password;

	

	private static final long EXPIRATIONTIME = 900000;

	public String authenticateUser(UserLoginDto user) throws UnauthorizedException {

		User userobj;
		try {
	      userobj = customerRegistrationRepository.findCustomerByEmail(user.getEmail());
			
		}
		catch(NullPointerException e)
		{
			throw new UnauthorizedException("Invalid Credentials");

		}
		if (userobj == null) {
			logger.debug("email is invalid");
			throw new UnauthorizedException("Invalid Credentials");
		}

		if (!user.getPassword().equals(userobj.getPassword())) {
			logger.debug("Password is invalid");
			throw new UnauthorizedException("Invalid Credentials");
		}
		return Jwts.builder().setSubject(user.getEmail()).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey")
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();

	}

}
