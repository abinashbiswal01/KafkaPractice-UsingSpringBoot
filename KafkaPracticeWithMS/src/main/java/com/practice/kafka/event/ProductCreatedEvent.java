package com.practice.kafka.event;

import java.math.BigDecimal;

public class ProductCreatedEvent {
	
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductCreatedEvent(String productId, String title, BigDecimal price, Integer quantity) {
		this.productId = productId;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}

	//used for deserialization purpose by consumer MS
	public ProductCreatedEvent() {
		
	}

	//setters and getters will be used by consumer to set the values after creating the object using the no arg const.
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	

}