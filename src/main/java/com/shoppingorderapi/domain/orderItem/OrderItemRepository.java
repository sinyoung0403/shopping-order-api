package com.shoppingorderapi.domain.orderItem;

import java.util.List;

import com.shoppingorderapi.common.repository.BaseRepository;

public interface OrderItemRepository extends BaseRepository<OrderItem, Long> {

	List<OrderItem> findAllByOrder_Id(Long orderId);
}
