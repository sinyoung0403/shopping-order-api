package com.shoppingorderapi.presentation.dto.orderItem.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderItemResponseDto {
	private Long orderItemId;

	public static CreateOrderItemResponseDto of(Long orderItemId) {
		return new CreateOrderItemResponseDto(orderItemId);
	}
}
