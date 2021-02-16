package com.eatza.review.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name= "review")
@Getter @Setter @NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String reviewComments;
	private Long customerId;
	private Long restaurantId;
	
	public Review(String reviewComments, Long customerId, Long restaurantId) {
		super();
		this.reviewComments = reviewComments;
		this.customerId = customerId;
		this.restaurantId = restaurantId;
	}
	
	
}
