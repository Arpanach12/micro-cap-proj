package com.eatza.review.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.repository.ReviewRepository;
import com.eatza.review.service.reviewservice.ReviewServiceImpl;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReviewServiceTest {

		@InjectMocks
		private ReviewServiceImpl reviewService;

		@Mock
		private ReviewRepository reviewRepository;

		
		@Mock
		private RestTemplate restTemplate;
		
		@Test
		public void addreview_Test() throws ReviewException {

			ReviewDto reviewdto = new ReviewDto("best services", 1L,1L);
			when(reviewRepository.save(any(Review.class)))
			.thenReturn(new Review(reviewdto.getReviewComments(),reviewdto.getCustomerId(),reviewdto.getRestaurantId()));			
			
			Review review = reviewService.addreview(reviewdto);
			assertNotNull(review);


		}
		
		@Test(expected=ReviewException.class)
		public void addreview_Failed_Case_One() throws ReviewException {

			ReviewDto reviewdto = new ReviewDto();
			reviewdto=null;
			Review review=new Review();
			review=null;
			when(reviewRepository.save(any(Review.class)))
			.thenReturn(review);			
			 reviewService.addreview(reviewdto);
		}
		@Test(expected=ReviewException.class)
		public void addreview_Failed_Case_Two() throws ReviewException {

			ReviewDto reviewdto = new ReviewDto(null, 1L,1L);
			Review review=null;
//			review=null;
			when(reviewRepository.save(any(Review.class)))
			.thenReturn(review);			
			 reviewService.addreview(reviewdto);
		}
		@Test
		public void getCommentsByRestaurantId_Test() throws ReviewException {

			List<Review> reviewlistById = new ArrayList<Review>();
				Review review=new Review("best services", 1L,1L);
				reviewlistById.add(review);
			when(reviewRepository.findAllReviewByRestaurantId(anyLong())).thenReturn(reviewlistById);
			List<Review> reviewlist = reviewService.getCommentsByRestaurantId(1L);
			assertEquals(1, reviewlistById.size());

		}
		@Test(expected=Exception.class)
		public void getCommentsByRestaurantId_Failed() throws Exception {

			List<Review> reviewlistById = new ArrayList<Review>();
			when(reviewRepository.findAllReviewByRestaurantId(anyLong())).thenReturn(reviewlistById);
			reviewService.getCommentsByRestaurantId(1L);

		}
		
		@Test
		public void updateCommentsByRestaurantId_Test() throws Exception {

				Optional<Review> review=Optional.of(new Review("best services", 1L,1L));
      		when(reviewRepository.findById(1L)).thenReturn(review);
			Review reviewobj = reviewService.updateComments(1L, "ok");

			review.get().setReviewComments("ok");
			when(reviewRepository.save(review.get())).thenReturn(review.get());
			Review reviewob = reviewService.updateComments(1L, "ok");

			assertEquals("ok", review.get().getReviewComments());

	}
		@Test(expected = ReviewException.class)
		public void updateCommentsByRestaurantId_Failed() throws Exception {

				Optional<Review> review=Optional.empty();
      		when(reviewRepository.findById(1L)).thenReturn(review);
			reviewService.updateComments(1L, "ok");
//			Review reviewob = reviewService.updateComments(1L, "ok");
	}
}
