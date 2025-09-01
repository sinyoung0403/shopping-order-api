package com.shoppingorderapi.presentation.dto.product.response;

import lombok.Getter;

@Getter
public class FindAllProductResponseDto {
	private	Long id;

	private	String name;

	private int price;

	private int stock;

	private String imageUrl;

	public FindAllProductResponseDto(Long id, String name, int price, int stock, String imageUrl) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.imageUrl = imageUrl;
	}
}
