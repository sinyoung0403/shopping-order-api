package com.shoppingorderapi.domain.order.dto.request;

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
