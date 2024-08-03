package com.practice.kafka.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.practice.kafka.event.ProductCreatedEvent;
import com.practice.kafka.request.model.CreateProductRestModel;

@Service
public class ProductServiceImpl implements ProductService {

	private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;  //K-key and v-value of message
	
	/*
	 * @Override public String createProduct(CreateProductRestModel
	 * productRestModel) { String productId=UUID.randomUUID().toString(); //TODO:
	 * persist prod details in DB before publishing an event
	 * 
	 * //create and set the event object ProductCreatedEvent event= new
	 * ProductCreatedEvent(productId,
	 * productRestModel.getTitle(),productRestModel.getPrice(),productRestModel.
	 * getQty());
	 * 
	 * //this will send a message to kafka topic asynchronously and not wait for
	 * acknowledgement //that message was successfully persisted
	 * //kafkaTemplate.send("product-created-events-topic", productId, event);
	 * 
	 * //to send synchronously and get the acknowledgement
	 * CompletableFuture<SendResult<String, ProductCreatedEvent>> future=
	 * kafkaTemplate.send("product-created-events-topic", productId, event);
	 * 
	 * future.whenComplete((result,exception)->{ if(exception!=null) {
	 * LOGGER.error("********* FAILED TO SEND MESSAGE:: "+exception.getMessage());
	 * }else {
	 * LOGGER.info("********** Message sent successfully: "+result.getRecordMetadata
	 * ()); } });
	 * 
	 * //block the current thread till future is complete in async call
	 * //future.join(); LOGGER.info("********* Returning product id*****"); return
	 * productId; }
	 */

	
	@Override
	public String createProductAsync(CreateProductRestModel productRestModel) {
		String productId=UUID.randomUUID().toString();
		//TODO: persist prod details in DB before publishing an event
		
		//create and set the event object
		ProductCreatedEvent event= new ProductCreatedEvent(productId,
				productRestModel.getTitle(),productRestModel.getPrice(),productRestModel.getQuantity());

		//to send synchronously and get the acknowledgement
		CompletableFuture<SendResult<String, ProductCreatedEvent>> future=
				kafkaTemplate.send("product-created-events-topic", productId, event);
		
		future.whenComplete((result,exception)->{
			if(exception!=null) {
				LOGGER.error("********* FAILED TO SEND MESSAGE:: "+exception.getMessage());
			}else {
				LOGGER.info("********** Message sent successfully: "+result.getRecordMetadata());
			}
		});
		
		
		LOGGER.info("********* Returning product id*****");
		return productId;
	}
	
	@Override
	public String createProductSync(CreateProductRestModel productRestModel) throws Exception {
		String productId = UUID.randomUUID().toString();
		// TODO: persist prod details in DB before publishing an event

		// create and set the event object
		ProductCreatedEvent event = new ProductCreatedEvent(productId, productRestModel.getTitle(),
				productRestModel.getPrice(), productRestModel.getQuantity());

		LOGGER.info("before publishing a ProductCreatedEvent");
		// to send synchronously  and get the acknowledgement just remove CompletableFuture
		SendResult<String, ProductCreatedEvent> result = kafkaTemplate
				.send("product-created-events-topic", productId, event).get();
		LOGGER.info("Partition : "+result.getRecordMetadata().partition());
		LOGGER.info("Topic : "+result.getRecordMetadata().topic());
		LOGGER.info("Offset : "+result.getRecordMetadata().offset());
		
		LOGGER.info("********* Returning product id*****");
		return productId;
	}
}
