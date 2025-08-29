package com.shoppingorderapi.domain.order.dto.request;

import lombok.Getter;

@Getter
public class CreateCartOrderRequestDto {
	private Long cartId;

	private Long userId;
}
