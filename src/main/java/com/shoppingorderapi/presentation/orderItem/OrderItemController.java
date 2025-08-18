package com.shoppingorderapi.presentation.orderItem;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.orderItem.OrderItemService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.domain.orderItem.dto.response.FindOrderItemResponseDto;

@RestController
@RequiredArgsConstructor
public class OrderItemController {

	private final OrderItemService orderItemService;

	@GetMapping("/me/orders/orderItems/{orderItemId}")
	public ResponseEntity<BaseResponse<FindOrderItemResponseDto>> getOrderItem(
		@PathVariable Long orderItemId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(orderItemService.findOrderItem(orderItemId)));
	}

	@DeleteMapping("/me/orders/orderItems/{orderItemId}")
	public ResponseEntity<BaseResponse<Void>> deleteOrderItem(
		@PathVariable Long orderItemId
	) {
		orderItemService.deleteOrderItem(orderItemId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
