package com.shoppingorderapi.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCartOrderResponseDto {
	private Long orderId;

	public static CreateCartOrderResponseDto of(Long orderId) {
		return new CreateCartOrderResponseDto(orderId);
	}
}
