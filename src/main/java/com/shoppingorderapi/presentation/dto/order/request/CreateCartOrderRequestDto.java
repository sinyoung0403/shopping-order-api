package com.shoppingorderapi.presentation.dto.order.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class CreateCartOrderRequestDto {

	@NotNull @Positive
	private Long cartId;

	@NotNull @Positive
	private Long userId;
}
