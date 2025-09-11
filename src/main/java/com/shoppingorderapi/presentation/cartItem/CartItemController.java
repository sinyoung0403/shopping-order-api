package com.shoppingorderapi.presentation.cartItem;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.cartItem.CartItemService;
import com.shoppingorderapi.application.cartItem.query.FindCartItemQueryDto;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.presentation.dto.cartItem.request.CreateCartItemRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.request.UpdateQuantityRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.response.CreateCartItemResponseDto;
import com.shoppingorderapi.presentation.security.Auth;
import com.shoppingorderapi.presentation.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me/carts")
public class CartItemController {

	private final CartItemService cartItemService;

	@PostMapping("/items")
	public ResponseEntity<BaseResponse<CreateCartItemResponseDto>> createCartItem(
		@Auth AuthUser authUser,
		@Valid @RequestBody CreateCartItemRequestDto dto
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(BaseResponse.success(cartItemService.createCartItem(authUser.userId(), dto)));
	}

	@GetMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<FindCartItemQueryDto>> getCartItem(
		@Auth AuthUser authUser,
		@PathVariable Long cartItemId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(cartItemService.findCartItem(authUser.userId(), cartItemId)));
	}

	@PatchMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<Void>> updateQuantity(
		@Auth AuthUser authUser,
		@PathVariable Long cartItemId,
		@Valid @RequestBody UpdateQuantityRequestDto dto
	) {
		cartItemService.updateQuantity(authUser.userId(), cartItemId, dto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}

	@DeleteMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<Void>> deleteCartItem(
		@Auth AuthUser authUser,
		@PathVariable Long cartItemId
	) {
		cartItemService.deleteCartItem(authUser.userId(), cartItemId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
