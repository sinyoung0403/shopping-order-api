package com.shoppingorderapi.domain.cartItem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindCartItemResponseDto {

	/**
	 * Cart Item 고유식별자
	 */
	private Long cartItemId;

	/**
	 * Product 고유식별자
	 */
	private Long productId;

	/**
	 * 상품 이미지 링크
	 */
	private String imageUrl;

	/**
	 * 수량
	 */
	private int quantity;

	/**
	 * 상품 가격
	 */
	private int price;

	public static FindCartItemResponseDto of(Long cartItemId, Long productId, String imageUrl, int quantity,
		int price) {
		return new FindCartItemResponseDto(cartItemId, productId, imageUrl, quantity, price);
	}
}
