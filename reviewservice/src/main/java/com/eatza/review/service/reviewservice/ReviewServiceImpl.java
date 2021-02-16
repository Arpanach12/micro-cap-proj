package com.eatza.review.service.reviewservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	public Review addreview(ReviewDto reviewdto) throws ReviewException {
		
		if(reviewdto.getReviewComments().equals(null)) {
			// handle exception properly later
			throw new ReviewException("please provide valuable feedback");
		}
		logger.debug("In add review method, creating review object to persist");

		Review review = new Review(reviewdto.getReviewComments(),reviewdto.getCustomerId(),reviewdto.getRestaurantId());
		logger.debug("saving review in db");
		Review savedreview = reviewRepository.save(review);
		return savedreview;
	}
	
	@Override
	public List<Review> getCommentsByRestaurantId(Long restaurantId) {

		 List<Review> reviewlistById=reviewRepository.findAllReviewByRestaurantId(restaurantId);
		return reviewlistById;
	}

	@Override
	public Review updateComments(Long id, String reviewComments) {

		 Review review=reviewRepository.findById(id).get();
		 review.setReviewComments(reviewComments);
		 Review savedreview= reviewRepository.save(review);

		return savedreview;
	}

	

	
}
