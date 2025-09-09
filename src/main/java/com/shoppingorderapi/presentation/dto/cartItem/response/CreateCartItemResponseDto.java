package com.shoppingorderapi.presentation.dto.cartItem.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCartItemResponseDto {
	/**
	 * CartItem 고유식별자
	 */
	private Long cartItemId;

	public static CreateCartItemResponseDto of(Long cartItemId) {
		return new CreateCartItemResponseDto(cartItemId);
	}
}
