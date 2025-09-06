package com.shoppingorderapi.presentation.dto.order.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shoppingorderapi.domain.order.OrderStatus;
import com.shoppingorderapi.presentation.dto.orderItem.response.OrderItemResponseDto;

@Getter
@AllArgsConstructor
public class FindOrderResponseDto {

	private OrderStatus status;

	private List<OrderItemResponseDto> orderItemList;

	private int totalCount;

	private int totalPrice;

	public static FindOrderResponseDto of(OrderStatus status, List<OrderItemResponseDto> orderItemList, int totalCount,
		int totalPrice) {
		return new FindOrderResponseDto(status, orderItemList, totalCount, totalPrice);
	}
}
