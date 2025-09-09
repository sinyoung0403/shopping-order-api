package com.shoppingorderapi.application.cartItem.query;

public record FindCartItemQueryDto(Long cartItemId, Long productId, String imageUrl, Integer quantity, Integer price) {
}
