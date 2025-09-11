package com.shoppingorderapi.presentation.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
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
import com.shoppingorderapi.application.order.query.FindAllOrderQueryDto;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.presentation.dto.order.request.CreateCartOrderRequestDto;
import com.shoppingorderapi.presentation.dto.order.request.CreateInstantOrderRequestDto;
import com.shoppingorderapi.presentation.dto.order.response.CreateCartOrderResponseDto;
import com.shoppingorderapi.presentation.dto.order.response.CreateInstantOrderResponseDto;
import com.shoppingorderapi.presentation.dto.order.response.FindOrderResponseDto;
import com.shoppingorderapi.presentation.security.Auth;
import com.shoppingorderapi.presentation.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class OrderController {

	private final OrderService orderService;

	// 즉시구입
	@PostMapping("/orders")
	public ResponseEntity<BaseResponse<CreateInstantOrderResponseDto>> createInstantOrder(
		@Auth AuthUser authUser,
		@Valid @RequestBody CreateInstantOrderRequestDto dto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.success(orderService.createInstantOrder(authUser.userId(), dto)));
	}

	// 장바구니 구입
	@PostMapping("/carts/orders")
	public ResponseEntity<BaseResponse<CreateCartOrderResponseDto>> createCartOrder(
		@Auth AuthUser authUser,
		@Valid @RequestBody CreateCartOrderRequestDto dto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.success(orderService.createCartOrder(authUser.userId(), dto)));
	}

	// 단건 주문 조회
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<BaseResponse<FindOrderResponseDto>> findOrder(
		@Auth AuthUser authUser,
		@PathVariable Long orderId
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(orderService.findOrder(authUser.userId(), orderId)));
	}

	// 전체 주문 조회
	@GetMapping("/orders")
	public ResponseEntity<BaseResponse<Page<FindAllOrderQueryDto>>> findAllOrder(
		@Auth AuthUser authUser,
		@PositiveOrZero @RequestParam(defaultValue = "0") int page,
		@Positive @Max(100) @RequestParam(defaultValue = "5") int size
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(orderService.findAllOrder(authUser.userId(), page, size)));
	}

	// 주문 취소
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<BaseResponse<Void>> cancelOrder(
		@Auth AuthUser authUser,
		@PathVariable Long orderId
	) {
		orderService.cancelOrder(authUser.userId(), orderId);
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}

