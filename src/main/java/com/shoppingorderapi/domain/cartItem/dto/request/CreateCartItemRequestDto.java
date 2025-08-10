package com.shoppingorderapi.domain.cartItem.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class CreateCartItemRequestDto {

	@NotNull(message = "상품 ID는 필수 입력 값입니다.")
	@Positive(message = "상품 ID는 1 이상의 값이어야 합니다.")
	private Long productId;

	@NotNull(message = "상품 수량은 필수 입력 값입니다.")
	@Positive(message = "상품 수량은 1 이상의 값이어야 합니다.")
	@Max(value = 100, message = "상품 수량은 최대 100까지 가능합니다.")
	private Integer quantity;
}
