package com.shoppingorderapi.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCartResponseDto {
	private Long cartId;

	public static CreateCartResponseDto of(Long cartId) {
		return new CreateCartResponseDto(cartId);
	}
}
