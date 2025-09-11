package com.shoppingorderapi.presentation.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.cart.CartService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.presentation.dto.cart.response.CartDetailResponseDto;
import com.shoppingorderapi.presentation.security.Auth;
import com.shoppingorderapi.presentation.security.AuthUser;

@RestController
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping("/me/carts")
	public ResponseEntity<BaseResponse<CartDetailResponseDto>> getCartItem(
		@Auth AuthUser authUser
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(cartService.findCart(authUser.userId())));
	}

	@DeleteMapping("/me/carts")
	public ResponseEntity<BaseResponse<Void>> deleteCart(
		@Auth AuthUser authUser
	) {
		cartService.deleteCart(authUser.userId());
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
