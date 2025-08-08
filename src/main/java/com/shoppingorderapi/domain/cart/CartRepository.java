package com.shoppingorderapi.domain.cart;

import java.util.Optional;

import com.shoppingorderapi.common.repository.BaseRepository;

public interface CartRepository extends BaseRepository<Cart, Long> {

	Optional<Cart> findByUser_Id(Long userId);
}
