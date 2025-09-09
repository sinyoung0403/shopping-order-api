package com.shoppingorderapi.presentation.dto.cart.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shoppingorderapi.application.cart.query.CartItemQueryDto;

@Getter
@AllArgsConstructor
public class CartDetailResponseDto {

	/**
	 * Cart 고유식별자
	 */
	private Long cartId;

	/**
	 * 장바구니에 담긴 서로 다른 상품 종류의 개수
	 */
	private int itemCount;

	/**
	 * 장바구니에 담긴 모든 상품의 총 수량 (같은 상품 여러 개 포함)
	 */
	private int totalQuantity;

	/**
	 * 총 가격
	 */
	private int totalAmount;

	/**
	 * 장바구니에 담긴 상품들
	 */
	private List<CartItemQueryDto> items;

	public static CartDetailResponseDto of(Long cartId, int itemCount, int totalQuantity, int totalAmount,
		List<CartItemQueryDto> items) {
		return new CartDetailResponseDto(cartId, itemCount, totalQuantity, totalAmount, items);
	}
}
