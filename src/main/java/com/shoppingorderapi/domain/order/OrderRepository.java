package com.shoppingorderapi.domain.order;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.order.dto.response.FindAllOrderResponseDto;

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
