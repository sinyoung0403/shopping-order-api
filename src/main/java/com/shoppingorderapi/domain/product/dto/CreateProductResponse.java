package com.shoppingorderapi.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductResponse {
	private Long productId;

	public static CreateProductResponse of(Long productId) {
		return new CreateProductResponse(productId);
	}
}
