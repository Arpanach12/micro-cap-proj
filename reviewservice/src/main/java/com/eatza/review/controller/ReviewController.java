package com.eatza.review.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.eatza.review.dto.ReviewDto;
import com.eatza.review.exception.ReviewException;
import com.eatza.review.model.Review;
import com.eatza.review.service.reviewservice.ReviewService;



@RestController
public class ReviewController {

	@Autowired
	ReviewService reviewService;

	@Autowired
	KafkaTemplate<String, ReviewDto> kafkaTemplate;
	
	private static final String KAFKA_TOPIC="NotifyReviewMessage";
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@PostMapping("/review")
	public ResponseEntity<Review> ReviewComments(@RequestHeader String authorization, @RequestBody ReviewDto reviewdto) throws ReviewException{
		logger.debug("In add review method, calling the service");
		Review review = reviewService.addreview(reviewdto);
		kafkaTemplate.send(KAFKA_TOPIC,reviewdto);
		logger.debug("review added Successfully");
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(review);

}
	
	@GetMapping("/review/{restaurantId}")
	public ResponseEntity<List<Review>> ReviewsByRestaurantId(@RequestHeader String authorization, @PathVariable Long restaurantId) throws ReviewException{
		logger.debug("In get review by restaurantid method, calling service to get reviews by ID");
		List<Review> reviewlist = reviewService.getCommentsByRestaurantId(restaurantId);
		if(reviewlist.size()>0) {
			logger.debug("Got reviews from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(reviewlist);
		}
		else {
			logger.debug("No reviews were found");
			throw new ReviewException("No result found for specified inputs");
		}
	}
	
	@PutMapping("/review/{id}/{reviewComments}")
	public ResponseEntity<Review> updateReviewByRestaurantId(@RequestHeader String authorization, @PathVariable Long id, @PathVariable String reviewComments) throws ReviewException{
		logger.debug("In update review by reviewId method, calling service to get reviews by ID");
		Review reviewobj = reviewService.updateComments(id,reviewComments);
		if(reviewobj!=null) {
			logger.debug("Got reviews from service");
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(reviewobj);
		}
		else {
			logger.debug("No reviews were found");
			throw new ReviewException("No result found for specified inputs");
		}
	}
	
}
