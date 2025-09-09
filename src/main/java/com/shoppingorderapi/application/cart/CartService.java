package com.shoppingorderapi.application.cart;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.application.cart.query.CartItemQueryDto;
import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cart.Cart;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.infra.persistence.jpa.CartItemRepository;
import com.shoppingorderapi.infra.persistence.jpa.CartRepository;
import com.shoppingorderapi.infra.persistence.jpa.UserRepository;
import com.shoppingorderapi.presentation.dto.cart.response.CartDetailResponseDto;

@Service
@RequiredArgsConstructor
public class CartService {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	@Transactional
	public Cart createCart(Long userId) {
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
		return cart;
	}

	@Transactional(readOnly = true)
	public CartDetailResponseDto findCart(Long userId) {
		// 1. UserId 에 맞는 카트 조회
		Cart cart = cartRepository.findByUser_Id(userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. CartItem 가져오기
		List<CartItemQueryDto> itemList = cartItemRepository.findCartItemListByCartIdDto(cart.getId());

		// 3. 아이템 개수
		int itemCount = itemList.size();

		// 4. 수량 합
		int totalQuantity = itemList.stream()
			.mapToInt(i -> i.quantity())
			.sum();

		// 5. 총 가격
		int totalAmount = itemList.stream()
			.mapToInt(i -> i.price() * i.quantity())
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
