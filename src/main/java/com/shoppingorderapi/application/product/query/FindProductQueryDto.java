package com.shoppingorderapi.application.product.query;

import java.time.LocalDateTime;

public record FindProductQueryDto(String name, Integer price, Integer stock, String description, String imageUrl,
								  LocalDateTime createdAt) {
}
