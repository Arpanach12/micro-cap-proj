package com.eatza.review.service.reviewservice;

import java.util.List;

import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;

public interface ReviewService {

	Review addreview(ReviewDto reviewdto) throws ReviewException;

	List<Review> getCommentsByRestaurantId(Long restaurantId);


	Review updateComments(Long id, String reviewComments);

}
