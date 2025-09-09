package com.shoppingorderapi.application.product.query;

public record FindAllProductQueryDto(Long id, String name, Integer price, Integer stock, String imageUrl) {
}
