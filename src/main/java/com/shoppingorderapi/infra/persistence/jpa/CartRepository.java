package com.shoppingorderapi.infra.persistence.jpa;

import java.util.Optional;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.cart.Cart;

public interface CartRepository extends BaseRepository<Cart, Long> {

	Optional<Cart> findByUser_Id(Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Cart> findByIdAndUser_Id(Long cartId, Long userId);

	boolean existsByUser_Id(Long userId);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(
		"DELETE FROM Cart c "
			+ "WHERE c.user.id = :userId"
	)
	int deleteByUserId(@Param("userId") Long userId);
}
