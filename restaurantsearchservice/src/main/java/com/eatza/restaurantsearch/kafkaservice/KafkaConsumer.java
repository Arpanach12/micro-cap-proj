package com.eatza.restaurantsearch.kafkaservice;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import com.eatza.restaurantsearch.dto.ReviewDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {
	
	
	
	@Autowired
	ObjectMapper objectMapper;
	

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(topics="NotifyReviewMessage",groupId="group_json",containerFactory="userListenerFactory")
    public void triggermailtocustomer(ReviewDto data) throws JsonParseException, JsonMappingException, IOException
  {
		
      System.out.println("Thank you for your response");
  }
	
}
