package com.shoppingorderapi.application.orderItem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.cartItem.dto.request.CreateCartItemRequestDto;
import com.shoppingorderapi.domain.order.Order;
import com.shoppingorderapi.domain.order.OrderRepository;
import com.shoppingorderapi.domain.orderItem.OrderItem;
import com.shoppingorderapi.domain.orderItem.OrderItemRepository;
import com.shoppingorderapi.domain.orderItem.dto.response.CreateOrderItemResponseDto;
import com.shoppingorderapi.domain.orderItem.dto.response.FindOrderItemResponseDto;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.domain.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Transactional
	public CreateOrderItemResponseDto orderItemCreate(Long userId, Long orderId, CreateCartItemRequestDto dto) {

		Product product = productRepository.findByIdOrElseThrow(dto.getProductId());

		Order order = orderRepository.findByIdOrElseThrow(orderId);

		OrderItem orderItem = OrderItem.builder()
			.order(order)
			.product(product)
			.productNameSnapshot(product.getName())
			.unitPriceSnapshot(product.getPrice())
			.quantity(dto.getQuantity())
			.lineTotal(product.getPrice() * dto.getQuantity())
			.build();

		orderItemRepository.save(orderItem);

		return CreateOrderItemResponseDto.of(orderItem.getId());
	}

	@Transactional
	public FindOrderItemResponseDto findOrderItem(Long orderItemId) {

		OrderItem orderItem = orderItemRepository.findByIdOrElseThrow(orderItemId);

		return FindOrderItemResponseDto.of(
			orderItem.getId(),
			orderItem.getProduct().getId(),
			orderItem.getProduct().getImageUrl(),
			orderItem.getProductNameSnapshot(),
			orderItem.getUnitPriceSnapshot(),
			orderItem.getQuantity(),
			orderItem.getLineTotal()
		);
	}

	@Transactional
	public void deleteOrderItem(Long orderItemId) {
		OrderItem orderItem = orderItemRepository.findByIdOrElseThrow(orderItemId);

		if (orderItem.getIsDeleted()) {
			throw new CustomException(ErrorCode.INVALID_INPUT);
		}

		orderItem.delete();
	}
}
