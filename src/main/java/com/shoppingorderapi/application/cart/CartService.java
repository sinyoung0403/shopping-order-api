package com.shoppingorderapi.application.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cart.Cart;
import com.shoppingorderapi.domain.cart.CartRepository;
import com.shoppingorderapi.domain.cart.dto.response.CartDetailResponseDto;
import com.shoppingorderapi.domain.cart.dto.response.CartItemResponseDto;
import com.shoppingorderapi.domain.cartItem.CartItemRepository;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CartService {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	@Transactional
	public CreateCartResponseDto createCart(Long userId) {
		// 1. User 존재 여부 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		if (cartRepository.existsByUser_Id(user.getId())) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		// 2. Cart Entity 생성
		Cart cart = Cart.builder().user(user).build();

		// 3. Cart 저장
		cartRepository.save(cart);

		// 4. 반환
		return CreateCartResponseDto.of(cart.getId());
	}

	public void findCart() {
		// Cart Item 추가 -> DTO 만든 후 다시 수정할 예정
	@Transactional(readOnly = true)
	public CartDetailResponseDto findCart(Long userId) {
		// 1. UserId 에 맞는 카트 조회
		Cart cart = cartRepository.findByUser_Id(userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. CartItem 가져오기
		List<CartItemResponseDto> itemList = cartItemRepository.findCartItemListByCartIdDto(cart.getId());

		// 3. 아이템 개수
		int itemCount = itemList.size();

		// 4. 수량 합
		int totalQuantity = itemList.stream()
			.mapToInt(i -> Optional.ofNullable(i.getQuantity()).orElse(0))
			.sum();

		// 5. 총 가격
		int totalAmount = itemList.stream()
			.mapToInt(i -> Optional.ofNullable(i.getPrice()).orElse(0)
				* Optional.ofNullable(i.getQuantity()).orElse(0))
			.sum();

		// 6. 반환
		return CartDetailResponseDto.of(cart.getId(), itemCount, totalQuantity, totalAmount, itemList);
	}

	@Transactional
	public void deleteCart(Long userId) {
		// 1. CartItem 먼저 삭제
		cartItemRepository.deleteByUserId(userId);
		// 2. Cart 삭제
		cartRepository.deleteByUserId(userId);
	}
}
