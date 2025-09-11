package com.shoppingorderapi.domain.user;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;

public enum UserRole {
	USER, OWNER;

	public static UserRole of(String role) {
		if (role == null || role.isBlank()) {
			throw new CustomException(ErrorCode.ROLE_REQUIRED);
		}
		String normalized = role.toUpperCase(java.util.Locale.ROOT);
		if (normalized.startsWith("ROLE_")) {
			normalized = normalized.substring(5);
		}
		return UserRole.valueOf(normalized);
	}
}
