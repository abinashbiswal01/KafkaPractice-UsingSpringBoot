package com.practice.kafka.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.kafka.request.model.CreateProductRestModel;
import com.practice.kafka.response.model.ErrorMessage;
import com.practice.kafka.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final Logger LOGGER =LoggerFactory.getLogger(this.getClass());
	ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody CreateProductRestModel product){
		
		String productId;
		try {
			productId = productService.createProductSync(product);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorMessage(new Date(),e.getMessage(),"/details"));
		}
		//String productId=productService.createProductAsync(product);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	}
}
