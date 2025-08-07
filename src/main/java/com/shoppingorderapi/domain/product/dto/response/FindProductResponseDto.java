package com.shoppingorderapi.domain.product.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindProductResponseDto {

	private String name;

	private int price;

	private int stock;

	private String description;

	private String imageUrl;

	private LocalDateTime createdAt;

	@Builder
	public FindProductResponseDto(String name, int price, int stock, String description, String imageUrl,
		LocalDateTime createdAt) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
	}
}
