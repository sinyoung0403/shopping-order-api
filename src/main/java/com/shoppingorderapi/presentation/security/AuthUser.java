package com.shoppingorderapi.presentation.security;

import com.shoppingorderapi.domain.user.UserRole;

public record AuthUser(Long userId, String email, UserRole role) {
}
