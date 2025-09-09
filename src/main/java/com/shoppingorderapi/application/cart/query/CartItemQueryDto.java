package com.shoppingorderapi.application.cart.query;

public record CartItemQueryDto(
	Long cartItemId,
	Long productId,
	String productName,
	String imageUrl,
	Integer quantity,
	Integer price
) {}
