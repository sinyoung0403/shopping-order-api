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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.cartItem.CartItemService;
import com.shoppingorderapi.application.cartItem.query.FindCartItemQueryDto;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.presentation.dto.cartItem.request.CreateCartItemRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.request.UpdateQuantityRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.response.CreateCartItemResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me/carts")
public class CartItemController {

	private final CartItemService cartItemService;

	// TODO: 추후 userId 를 Param 이 아닌, 토큰 속에서 값을 가져올 것이다.
	@PostMapping("/items")
	public ResponseEntity<BaseResponse<CreateCartItemResponseDto>> createCartItem(
		@RequestParam Long userId,
		@Valid @RequestBody CreateCartItemRequestDto dto
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(BaseResponse.success(cartItemService.createCartItem(userId, dto)));
	}

	@GetMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<FindCartItemQueryDto>> getCartItem(
		@RequestParam Long userId,
		@PathVariable Long cartItemId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(cartItemService.findCartItem(userId, cartItemId)));
	}

	@PatchMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<Void>> updateQuantity(
		@RequestParam Long userId,
		@PathVariable Long cartItemId,
		@Valid @RequestBody UpdateQuantityRequestDto dto
	) {
		cartItemService.updateQuantity(userId, cartItemId, dto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}

	@DeleteMapping("/items/{cartItemId}")
	public ResponseEntity<BaseResponse<Void>> deleteCartItem(
		@RequestParam Long userId,
		@PathVariable Long cartItemId
	) {
		cartItemService.deleteCartItem(userId, cartItemId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
