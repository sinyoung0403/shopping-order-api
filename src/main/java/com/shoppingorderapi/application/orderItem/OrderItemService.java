package com.shoppingorderapi.application.orderItem;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.order.Order;
import com.shoppingorderapi.infra.persistence.jpa.OrderRepository;
import com.shoppingorderapi.domain.orderItem.OrderItem;
import com.shoppingorderapi.infra.persistence.jpa.OrderItemRepository;
import com.shoppingorderapi.presentation.dto.orderItem.response.FindOrderItemResponseDto;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.infra.persistence.jpa.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Transactional
	public OrderItem orderItemCreate(Long orderId, Long productId, int quantity) {

		Product product = productRepository.findByIdOrElseThrow(productId);

		Order order = orderRepository.findByIdOrElseThrow(orderId);

		OrderItem orderItem = OrderItem.builder()
			.order(order)
			.product(product)
			.productNameSnapshot(product.getName())
			.unitPriceSnapshot(product.getPrice())
			.quantity(quantity)
			.lineTotal(product.getPrice() * quantity)
			.build();

		orderItemRepository.save(orderItem);

		return orderItem;
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
