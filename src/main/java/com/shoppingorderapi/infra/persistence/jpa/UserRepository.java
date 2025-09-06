package com.shoppingorderapi.infra.persistence.jpa;

import java.util.Optional;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.user.User;

public interface UserRepository extends BaseRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
