package com.eatza.customer.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReviewDto {

	private String reviewComments;
	private Long customerId;
	private Long restaurantId;
	
	public ReviewDto(String reviewComments, Long customerId, Long restaurantId) {
		super();
		this.reviewComments = reviewComments;
		this.customerId = customerId;
		this.restaurantId = restaurantId;
	}
}
