package com.shoppingorderapi.presentation.dto.order.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class CreateInstantOrderRequestDto {

	@NotNull
	@Positive
	private Long productId;

	@Min(1)
	@Max(100)
	private int quantity;

}
