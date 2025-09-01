package com.shoppingorderapi.presentation.dto.order.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.shoppingorderapi.domain.order.OrderStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAllOrderResponseDto {
	private Long orderId;

	private OrderStatus orderStatus;

	private int totalCount;

	private int totalPrice;

	public FindAllOrderResponseDto(Long orderId, OrderStatus orderStatus, int totalCount, int totalPrice) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.totalCount = totalCount;
		this.totalPrice = totalPrice;
	}
}
