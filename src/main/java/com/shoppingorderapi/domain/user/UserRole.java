package com.shoppingorderapi.domain.user;

public enum	UserRole {
	USER, OWNER;

	public static UserRole of(String role) {
		return UserRole.valueOf(role.toUpperCase());
	}
}
