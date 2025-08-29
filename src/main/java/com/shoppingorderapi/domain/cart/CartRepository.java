package com.shoppingorderapi.domain.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;

public interface CartRepository extends BaseRepository<Cart, Long> {

	Optional<Cart> findByUser_Id(Long userId);

	Optional<Cart> findByIdAndUser_Id(Long cartId, Long userId);

	boolean existsByUser_Id(Long userId);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(
		"DELETE FROM Cart c "
			+ "WHERE c.user.id = :userId"
	)
	int deleteByUserId(@Param("userId") Long userId);
}
