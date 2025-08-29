package com.shoppingorderapi.domain.order.dto.request;

import lombok.Getter;

@Getter
public class CreateInstantOrderRequestDto {

	private Long userId;

	private Long productId;

	private int quantity;
}
