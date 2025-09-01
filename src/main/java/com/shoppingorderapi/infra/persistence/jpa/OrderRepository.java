package com.shoppingorderapi.infra.persistence.jpa;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.order.Order;
import com.shoppingorderapi.presentation.dto.order.response.FindAllOrderResponseDto;

public interface OrderRepository extends BaseRepository<Order, Long> {
	Optional<Order> findByIdAndUser_Id(Long orderId, Long userId);

	@Query(
		"SELECT new com.shoppingorderapi.domain.order.dto.response.FindAllOrderResponseDto(" +
			"o.id, o.status, o.totalItemCount, o.totalItemPrice) " +
			"FROM Order o " +
			"WHERE o.user.id = :userId"
	)
	Page<FindAllOrderResponseDto> findAllOrderByUserId(@Param("userId") Long userId, Pageable pageable);
}
