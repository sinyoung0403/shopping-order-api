package com.shoppingorderapi.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductResponseDto {
	private Long productId;

	public static CreateProductResponseDto of(Long productId) {
		return new CreateProductResponseDto(productId);
	}
}
