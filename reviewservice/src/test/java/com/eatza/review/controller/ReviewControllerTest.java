package com.eatza.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import com.eatza.review.ReviewserviceApplication;
import com.eatza.review.dto.ReviewDto;
import com.eatza.review.model.Review;
import com.eatza.review.service.reviewservice.ReviewServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@ContextConfiguration(classes= ReviewserviceApplication.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value= ReviewController.class)
public class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewServiceImpl orderService;
	
	@MockBean
	private KafkaTemplate kafkaTemplate;
	
	

//	@Autowired
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
	public void ReviewComments() throws Exception {
		ReviewDto reviewdto = new ReviewDto();
		Review review = new Review("best services", 1L,1L);
		reviewdto.setCustomerId(1L);
		reviewdto.setRestaurantId(1L);
		reviewdto.setReviewComments("best services");
		when(orderService.addreview(any(ReviewDto.class))).thenReturn(review);
		kafkaTemplate.send("message",ReviewDto.class);
		RequestBuilder request = MockMvcRequestBuilders.post(
				"/review")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((reviewdto)))
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();
	}
	@Test
	public void ReviewsByRestaurantId() throws Exception {

		List<Review> reviewlist = new ArrayList<Review>();
		Review review = new Review("best services", 1L,1L);
		reviewlist.add(review);
		when(orderService.getCommentsByRestaurantId(anyLong())).thenReturn(reviewlist);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/review/1")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();



	}
	
	@Test
	public void ReviewsByRestaurantId_failed() throws Exception {

		List<Review> reviewlist = new ArrayList<Review>();
		when(orderService.getCommentsByRestaurantId(anyLong())).thenReturn(reviewlist);
		RequestBuilder request = MockMvcRequestBuilders.get(
				"/review/1")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();
	}
	
	@Test
	public void updateCommentsByReviewId_Test() throws Exception {

		Review review = new Review("delicious", 1L,1L);
		when(orderService.updateComments(1L, "delicious")).thenReturn(review);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/review/1/delicious")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(200))
		.andReturn();



	}
	@Test
	public void updateCommentsByReviewId_() throws Exception {

		Review review = new Review();
		review=null;
		when(orderService.updateComments(17L, "delicious")).thenReturn(review);
		RequestBuilder request = MockMvcRequestBuilders.put(
				"/review/17/delicious")
				.accept(MediaType.ALL)
				.header(HttpHeaders.AUTHORIZATION,
						jwt);
		mockMvc.perform(request)
		.andExpect(status().is(400))
		.andReturn();

	}
	
	
}
