package com.shoppingorderapi.infra.persistence.jpa;

import java.util.List;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.orderItem.OrderItem;

public interface OrderItemRepository extends BaseRepository<OrderItem, Long> {

	List<OrderItem> findAllByOrder_Id(Long orderId);
}
