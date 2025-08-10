package com.shoppingorderapi.presentation.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.cart.CartService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.domain.cart.dto.response.CartDetailResponseDto;

@RestController
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	// TODO: 추후 Security 도입 후 user Id 토큰 속에서 받아오기
	@GetMapping("/me/carts")
	public ResponseEntity<BaseResponse<CartDetailResponseDto>> getCartItem(
		@RequestParam Long userId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(cartService.findCart(userId)));
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
