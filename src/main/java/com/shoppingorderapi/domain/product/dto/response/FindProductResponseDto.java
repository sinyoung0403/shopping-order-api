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

	private LocalDateTime created_at;

	@Builder
	public FindProductResponseDto(String name, int price, int stock, String description, String imageUrl,
		LocalDateTime created_at) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.imageUrl = imageUrl;
		this.created_at = created_at;
	}
}
