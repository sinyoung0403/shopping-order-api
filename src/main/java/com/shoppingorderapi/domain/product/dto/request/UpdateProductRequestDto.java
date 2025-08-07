package com.shoppingorderapi.domain.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;

@Getter
public class UpdateProductRequestDto {

	@Size(min = 1, max = 100)
	private String name;

	@Positive
	@Max(10000000)
	private Integer price;

	@Size(min = 1, max = 1000)
	private String description;

	private String imageUrl;
}
