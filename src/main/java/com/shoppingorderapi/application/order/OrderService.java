package com.shoppingorderapi.application.order;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.application.cart.CartService;
import com.shoppingorderapi.application.order.query.FindAllOrderQueryDto;
import com.shoppingorderapi.application.orderItem.OrderItemService;
import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cart.Cart;
import com.shoppingorderapi.domain.cartItem.CartItem;
import com.shoppingorderapi.domain.order.Order;
import com.shoppingorderapi.domain.order.OrderStatus;
import com.shoppingorderapi.domain.orderItem.OrderItem;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.infra.persistence.jpa.CartItemRepository;
import com.shoppingorderapi.infra.persistence.jpa.CartRepository;
import com.shoppingorderapi.infra.persistence.jpa.OrderItemRepository;
import com.shoppingorderapi.infra.persistence.jpa.OrderRepository;
import com.shoppingorderapi.infra.persistence.jpa.ProductRepository;
import com.shoppingorderapi.infra.persistence.jpa.UserRepository;
import com.shoppingorderapi.presentation.dto.order.request.CreateCartOrderRequestDto;
import com.shoppingorderapi.presentation.dto.order.request.CreateInstantOrderRequestDto;
import com.shoppingorderapi.presentation.dto.order.response.CreateCartOrderResponseDto;
import com.shoppingorderapi.presentation.dto.order.response.CreateInstantOrderResponseDto;
import com.shoppingorderapi.presentation.dto.order.response.FindOrderResponseDto;
import com.shoppingorderapi.presentation.dto.orderItem.response.OrderItemResponseDto;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemService orderItemService;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartService cartService;

	@Transactional
	public CreateInstantOrderResponseDto createInstantOrder(CreateInstantOrderRequestDto dto) {

		// 1. 유저 및 상품 조회
		User user = userRepository.findByIdOrElseThrow(dto.getUserId());
		Product product = productRepository.findByIdOrElseThrow(dto.getProductId());

		// 2. 주문 생성
		Order order = Order.builder()
			.user(user)
			.status(OrderStatus.CREATED)
			.build();
		orderRepository.save(order);

		// 3. OrderItem 생성
		OrderItem orderItem = orderItemService.orderItemCreate(order.getId(), product.getId(), dto.getQuantity());

		// 4. 가격 및 개수 업데이트
		order.updateTotalItemPrice(orderItem.getLineTotal());
		order.updateTotalItemCount(1);

		return CreateInstantOrderResponseDto.of(order.getId());
	}

	@Transactional
	public CreateCartOrderResponseDto createCartOrder(CreateCartOrderRequestDto dto) {

		// 1. dto 에 맞는 유저와 장바구니 조회
		User user = userRepository.findByIdOrElseThrow(dto.getUserId());
		Cart cart = cartRepository.findByIdAndUser_Id(dto.getCartId(), dto.getUserId()).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. 카트 속의 아이템을 리스트에 담기.
		List<CartItem> cartItemList = cartItemRepository.findAllByCart_Id(cart.getId());

		// 3. 카트 아이템이 없다면 예외 발생
		if (cartItemList.isEmpty()) {
			throw new CustomException(ErrorCode.CART_EMPTY);
		}

		// 4. 먼저 Order 를 만들어 준다.
		Order order = Order.builder()
			.user(user)
			.status(OrderStatus.CREATED)
			.build();

		orderRepository.save(order);

		// 5. CartItem 을 Order Item 으로 변환
		List<OrderItem> orderItemList = cartItemList.stream().map(
			n -> orderItemService.orderItemCreate(order.getId(), n.getProduct().getId(), n.getQuantity())
		).toList();

		// 6. CartItem 의 총 가격 및 총 개수 반영
		order.updateTotalItemPrice(
			orderItemList.stream().mapToInt(OrderItem::getLineTotal).sum()
		);
		order.updateTotalItemCount(orderItemList.size());

		// 7. 장바구니 삭제
		cartService.deleteCart(user.getId());

		return CreateCartOrderResponseDto.of(order.getId());
	}

	@Transactional(readOnly = true)
	public FindOrderResponseDto findOrder(Long userId, Long orderId) {

		// 1. 주문 조회
		Order order = orderRepository.findByIdAndUser_Id(orderId, userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);

		// 2. OrderItem 조회
		List<OrderItem> orderItemList = orderItemRepository.findAllByOrder_Id(order.getId());

		// 3. OrderItem 의 List 를 Dto 형태로 변환
		List<OrderItemResponseDto> orderIemDtoList = orderItemList.stream()
			.map(OrderItemResponseDto::from)
			.toList();

		return FindOrderResponseDto.of(order.getStatus(), orderIemDtoList, order.getTotalItemCount(),
			order.getTotalItemPrice());
	}

	@Transactional(readOnly = true)
	public Page<FindAllOrderQueryDto> findAllOrder(Long userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
		return orderRepository.findAllOrderByUserId(userId, pageable);
	}

	@Transactional
	public void cancelOrder(Long userId, Long orderId) {
		// 1. 주문 조회
		Order order = orderRepository.findByIdAndUser_Id(orderId, userId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND)
		);
		// 2. 주문 취소
		order.cancel();
	}
}
