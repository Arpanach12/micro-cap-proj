package com.eatza.review.service.authenticationservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eatza.review.config.LoginCustomer;
import com.eatza.review.dto.UserDto;
import com.eatza.review.exception.UnauthorizedException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtAuthenticationService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LoginCustomer logincustomer;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationService.class);

//	@Value("${user}")
//	String username;
//
//	@Value("${password}")
//	String password;

	@Value("${customer.search.url}")
	private String customerServiceUrl;
	private static final long EXPIRATIONTIME = 900000;

	public String authenticateUser(UserDto user) throws UnauthorizedException {

//		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(customerServiceUrl).queryParam("email",
//				user.getEmail());

		UserDto userobj;
		try {
			logger.debug("Calling customer search service to get user details");
			userobj = logincustomer.customerByEmail(user.getEmail()).getBody();
		} catch (HttpClientErrorException e) {
			logger.debug("Email is invalid");
			throw new UnauthorizedException("Please check your email or register for a new account");
		}
		if (userobj == null) {

			throw new UnauthorizedException("Invalid Credentials");
		}

		if (!user.getPassword().equals(userobj.getPassword())) {
			logger.debug("Password is invalid");
			throw new UnauthorizedException("Invalid Credentials");

		}
		return Jwts.builder().setSubject(userobj.getEmail()).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey")
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).compact();

	}

}
