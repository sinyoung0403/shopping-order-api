package com.shoppingorderapi.application.cartItem;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.application.cart.CartService;
import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cart.Cart;
import com.shoppingorderapi.infra.persistence.jpa.CartRepository;
import com.shoppingorderapi.domain.cartItem.CartItem;
import com.shoppingorderapi.infra.persistence.jpa.CartItemRepository;
import com.shoppingorderapi.presentation.dto.cartItem.request.CreateCartItemRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.request.UpdateQuantityRequestDto;
import com.shoppingorderapi.presentation.dto.cartItem.response.CreateCartItemResponseDto;
import com.shoppingorderapi.presentation.dto.cartItem.response.FindCartItemResponseDto;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.infra.persistence.jpa.ProductRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final CartService cartService;
	private final ProductRepository productRepository;

	@Transactional
	public CreateCartItemResponseDto createCartItem(Long userId, CreateCartItemRequestDto dto) {
		// 1. user 에 맞는 카트가 존재하는지 여부 확인
		Cart cart = cartRepository.findByUser_Id(userId)
			.orElseGet(() -> {
				try {
					return cartService.createCart(userId);
				} catch (DataIntegrityViolationException e) {
					return cartRepository.findByUser_Id(userId)
						.orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
				}
			});

		// 2. Product 찾기
		Product product = productRepository.findByIdOrElseThrow(dto.getProductId());

		// 3. CartItem Entity 만들기
		CartItem cartItem = CartItem
			.builder()
			.cart(cart)
			.product(product)
			.quantity(dto.getQuantity())
			.build();

		// 4. save 하기
		cartItemRepository.save(cartItem);

		// 5. 반환
		return CreateCartItemResponseDto.of(cartItem.getId());
	}

	@Transactional(readOnly = true)
	public FindCartItemResponseDto findCartItem(Long userId, Long cartItemId) {
		return cartItemRepository.findCartItemDto(cartItemId, userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);
	}

	@Transactional
	public void updateQuantity(Long userId, Long cartItemId, UpdateQuantityRequestDto dto) {
		// 1. userId 일치 여부 확인
		Cart cart = cartRepository.findByUser_Id(userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. CartItem 가져오기
		CartItem cartItem = cartItemRepository.findCartItemByIdAndCart_Id(cartItemId, cart.getId()).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		if (cartItem.getQuantity() == dto.getQuantity()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		// 3. CartItem 의 수량 변경
		cartItem.updateQuantity(dto.getQuantity());
	}

	@Transactional
	public void deleteCartItem(Long userId, Long cartItemId) {
		// 1. userId 일치 여부 확인
		Cart cart = cartRepository.findByUser_Id(userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. CartItem 가져오기
		CartItem cartItem = cartItemRepository.findCartItemByIdAndCart_Id(cartItemId, cart.getId()).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 3. CartItem 삭제하기
		cartItemRepository.delete(cartItem);
	}
}
