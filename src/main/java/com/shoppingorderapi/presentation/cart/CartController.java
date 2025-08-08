package com.shoppingorderapi.presentation.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.cart.CartService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.domain.cart.dto.CreateCartResponseDto;

@RestController
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	// TODO: 추후 Security 도입 후 user Id 토큰 속에서 받아오기
	@PostMapping("/cart")
	public ResponseEntity<BaseResponse<CreateCartResponseDto>> createCart(
		@RequestParam Long userId
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(BaseResponse.success(cartService.createCart(userId)));
	}

	// TODO: 추후 Security 도입 후 user Id 토큰 속에서 받아오기
	// TODO: 추후 CartItem 생성 후 수정
	@GetMapping("/cart")
	public ResponseEntity<BaseResponse<Void>> getCart(
		@RequestParam Long userId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}

	@DeleteMapping("/cart")
	public ResponseEntity<BaseResponse<Void>> deleteCart(
		@RequestParam Long userId
	) {
		cartService.deleteCart(userId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
