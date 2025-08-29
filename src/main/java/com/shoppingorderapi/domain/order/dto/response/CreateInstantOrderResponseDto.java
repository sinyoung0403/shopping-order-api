package com.shoppingorderapi.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateInstantOrderResponseDto {
	private Long orderId;

	public static CreateInstantOrderResponseDto of(Long orderId) {
		return new CreateInstantOrderResponseDto(orderId);
	}
}
