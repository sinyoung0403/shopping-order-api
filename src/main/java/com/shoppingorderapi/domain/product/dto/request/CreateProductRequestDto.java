package com.shoppingorderapi.domain.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;

@Getter
public class CreateProductRequestDto {

	@NotBlank
	@Size(min = 1, max = 100)
	private String name;

	@NotNull
	@Positive
	@Max(10000000)
	private int price;

	@NotNull
	@Max(10000000)
	private int stock;

	@NotBlank
	@Size(min = 1, max = 1000)
	private String description;
}
