package com.shoppingorderapi.application.order.query;

import com.shoppingorderapi.domain.order.OrderStatus;

public record FindAllOrderQueryDto(Long orderId, OrderStatus orderStatus, Integer totalCount, Integer to) {
}
