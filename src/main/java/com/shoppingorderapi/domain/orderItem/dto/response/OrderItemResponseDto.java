package com.shoppingorderapi.domain.orderItem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shoppingorderapi.domain.orderItem.OrderItem;

@Getter
@AllArgsConstructor
public class OrderItemResponseDto {
	private Long productId;
	private String productName;
	private int unitPrice;
	private int quantity;
	private int lineTotal;

	public static OrderItemResponseDto from(OrderItem oi) {
		return new OrderItemResponseDto(
			oi.getProduct().getId(),
			oi.getProductNameSnapshot(),
			oi.getUnitPriceSnapshot(),
			oi.getQuantity(),
			oi.getLineTotal()
		);
	}
}
