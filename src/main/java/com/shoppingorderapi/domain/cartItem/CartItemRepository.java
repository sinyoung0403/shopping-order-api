package com.shoppingorderapi.domain.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.cart.dto.response.CartItemResponseDto;
import com.shoppingorderapi.domain.cartItem.dto.response.FindCartItemResponseDto;

public interface CartItemRepository extends BaseRepository<CartItem, Long> {

	@Query(
		"SELECT new com.shoppingorderapi.domain.cartItem.dto.response.FindCartItemResponseDto(c.id, p.id, p.imageUrl, c.quantity, p.price) "
			+ "FROM CartItem c JOIN c.product p "
			+ "WHERE c.id = :cartItemId "
			+ "AND c.cart.user.id = :userId"
	)
	Optional<FindCartItemResponseDto> findCartItemDto(
		@Param("cartItemId") Long cartItemId,
		@Param("userId") Long userId
	);

	@Query(
		"SELECT new com.shoppingorderapi.domain.cart.dto.response.CartItemResponseDto(ci.id, p.id, p.name, p.imageUrl, ci.quantity, p.price) "
			+ "FROM CartItem ci JOIN ci.product p "
			+ "WHERE ci.cart.id = :cartId"
	)
	List<CartItemResponseDto> findCartItemListByCartIdDto(
		@Param("cartId") Long cartId
	);

	Optional<CartItem> findCartItemByIdAndCart_Id(Long cartItemId, Long cartId);

	@Modifying
	@Query(
		"DELETE FROM CartItem ci "
			+ "WHERE ci.cart.user.id = :userId"
	)
	void deleteByUserId(@Param("userId") Long userId);
}
