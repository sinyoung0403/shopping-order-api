package com.shoppingorderapi.presentation.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.order.OrderService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.domain.order.dto.request.CreateCartOrderRequestDto;
import com.shoppingorderapi.domain.order.dto.request.CreateInstantOrderRequestDto;
import com.shoppingorderapi.domain.order.dto.response.CreateCartOrderResponseDto;
import com.shoppingorderapi.domain.order.dto.response.CreateInstantOrderResponseDto;
import com.shoppingorderapi.domain.order.dto.response.FindAllOrderResponseDto;
import com.shoppingorderapi.domain.order.dto.response.FindOrderResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class OrderController {

	private final OrderService orderService;

	// 즉시구입
	@PostMapping("/orders")
	public ResponseEntity<BaseResponse<CreateInstantOrderResponseDto>> createInstantOrder(
		@Valid @RequestBody CreateInstantOrderRequestDto dto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.success(orderService.createInstantOrder(dto)));
	}

	// 장바구니 구입
	@PostMapping("/carts/orders")
	public ResponseEntity<BaseResponse<CreateCartOrderResponseDto>> createCartOrder(
		@Valid @RequestBody CreateCartOrderRequestDto dto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.success(orderService.createCartOrder(dto)));
	}

	// 단건 주문 조회
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<BaseResponse<FindOrderResponseDto>> findOrder(
		// TODO : 추후에 변경 예정
		@RequestParam Long userId,
		@PathVariable Long orderId
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(orderService.findOrder(userId, orderId)));
	}

	// 전체 주문 조회
	@GetMapping("/orders")
	public ResponseEntity<BaseResponse<Page<FindAllOrderResponseDto>>> findAllOrder(
		@RequestParam Long userId,
		@PositiveOrZero @RequestParam(defaultValue = "0") int page,
		@Positive @RequestParam(defaultValue = "5") int size
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(orderService.findAllOrder(userId, page, size)));
	}

	// 주문 취소
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<BaseResponse<Void>> cancelOrder(
		@RequestParam Long userId,
		@PathVariable Long orderId
	) {
		orderService.cancelOrder(userId, orderId);
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}

