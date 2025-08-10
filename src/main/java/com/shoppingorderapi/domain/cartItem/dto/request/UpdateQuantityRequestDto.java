package com.shoppingorderapi.domain.cartItem.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class UpdateQuantityRequestDto {

	@NotNull(message = "수량은 필수입니다.")
	@Positive(message = "수량은 1 이상이어야 합니다.")
	@Max(value = 100, message = "수량은 최대 100까지 가능합니다.")
	private Integer quantity;
}
