package com.practice.kafka.service;

import com.practice.kafka.request.model.CreateProductRestModel;

public interface ProductService {

	String createProductAsync(CreateProductRestModel productRestModel);
	String createProductSync(CreateProductRestModel productRestModel)  throws Exception;
}
