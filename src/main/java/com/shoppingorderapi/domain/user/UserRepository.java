package com.shoppingorderapi.domain.user;

import java.util.Optional;

import com.shoppingorderapi.common.repository.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
