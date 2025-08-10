package com.shoppingorderapi.domain.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;

public interface CartRepository extends BaseRepository<Cart, Long> {

	Optional<Cart> findByUser_Id(Long userId);

	boolean existsByUser_Id(Long userId);

	@Modifying
	@Query(
		"DELETE FROM Cart c "
			+ "WHERE c.user.id = :userId"
	)
	void deleteByUserId(@Param("userId") Long userId);
}
