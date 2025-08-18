package com.shoppingorderapi.domain.orderItem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindOrderItemResponseDto {

	private Long orderItemId;

	private Long productId;

	private String productImageUrl;

	private String productName;

	private int unitPriceSnapshot;

	private int quantity;

	private int lineTotal;

	public static FindOrderItemResponseDto of(Long orderItemId, Long productId, String productImageUrl,
		String productName, int unitPriceSnapshot, int quantity, int lineTotal) {
		return new FindOrderItemResponseDto(orderItemId, productId, productImageUrl, productName, unitPriceSnapshot,
			quantity, lineTotal);
	}
}
