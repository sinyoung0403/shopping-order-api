package com.shoppingorderapi.domain.cart.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartItemResponseDto {

	/**
	 * CartItem 고유식별자
	 */
	private Long cartItemId;

	/**
	 * Product 고유식별자
	 */
	private Long productId;

	/**
	 * 상품 이름
	 */
	private String productName;

	/**
	 * 상품 이미지 링크
	 */
	private String imageUrl;

	/**
	 * 장바구니에 담긴 상품 수량
	 */
	private int quantity;

	/**
	 * 상품 가격
	 */
	private int price;

	@Builder
	public CartItemResponseDto(Long cartItemId, Long productId, String productName, String imageUrl, int quantity,
		int price) {
		this.cartItemId = cartItemId;
		this.productId = productId;
		this.productName = productName;
		this.imageUrl = imageUrl;
		this.quantity = quantity;
		this.price = price;
	}
}
