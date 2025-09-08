package com.shoppingorderapi.infra.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.cartItem.CartItem;
import com.shoppingorderapi.presentation.dto.cart.response.CartItemResponseDto;
import com.shoppingorderapi.presentation.dto.cartItem.response.FindCartItemResponseDto;

public interface CartItemRepository extends BaseRepository<CartItem, Long> {

	@Query(
		"SELECT new com.shoppingorderapi.presentation.dto.cartItem.response.FindCartItemResponseDto(c.id, p.id, p.imageUrl, c.quantity, p.price) "
			+ "FROM CartItem c JOIN c.product p "
			+ "WHERE c.id = :cartItemId "
			+ "AND c.cart.user.id = :userId"
	)
	Optional<FindCartItemResponseDto> findCartItemDto(
		@Param("cartItemId") Long cartItemId,
		@Param("userId") Long userId
	);

	@Query(
		"SELECT new com.shoppingorderapi.presentation.dto.cart.response.CartItemResponseDto(ci.id, p.id, p.name, p.imageUrl, ci.quantity, p.price) "
			+ "FROM CartItem ci JOIN ci.product p "
			+ "WHERE ci.cart.id = :cartId"
	)
	List<CartItemResponseDto> findCartItemListByCartIdDto(
		@Param("cartId") Long cartId
	);

	Optional<CartItem> findCartItemByIdAndCart_Id(Long cartItemId, Long cartId);

	List<CartItem> findAllByCart_Id(Long cartId);

	// DML 쿼리 실행 시 영속성 컨텍스트와 DB 상태 불일치를 막기 위한 안전 장치
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(
		"DELETE FROM CartItem ci "
			+ "WHERE ci.cart.user.id = :userId"
	)
	int deleteByUserId(@Param("userId") Long userId);
}
