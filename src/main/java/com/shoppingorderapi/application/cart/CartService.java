package com.shoppingorderapi.application.cart;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cart.Cart;
import com.shoppingorderapi.domain.cart.CartRepository;
import com.shoppingorderapi.domain.cart.dto.CreateCartResponseDto;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CartService {

	private final UserRepository userRepository;
	private final CartRepository cartRepository;

	@Transactional
	public CreateCartResponseDto createCart(Long userId) {
		// 1. User 존재 여부 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		// 2. Cart Entity 생성
		Cart cart = Cart.builder().user(user).build();

		// 3. Cart 저장
		cartRepository.save(cart);

		// 4. 반환
		return CreateCartResponseDto.of(cart.getId());
	}

	public void findCart() {
		// Cart Item 추가 -> DTO 만든 후 다시 수정할 예정
	}

	public void deleteCart(Long userId) {
		// 1. User 에 맞는 카트 확인
		Cart cart = cartRepository.findByUser_Id(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		// 2. 아이템 먼저 삭제
		// TODO: 추후 구현 예정

		// 3. cart 삭제
		cartRepository.delete(cart);
	}
}
