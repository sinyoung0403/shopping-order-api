package com.shoppingorderapi.presentation.security;

import java.util.Collection;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long userId;
	private final String email;
	private final Collection<? extends GrantedAuthority> authorities;

	public static CustomUserDetails of(Long userId, String email, Collection<? extends GrantedAuthority> authorities) {
		return new CustomUserDetails(userId, email, authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// Jwt 인증방식이므로 해당 부분 공란
	@Override
	public String getPassword() {
		return "";
	}

	// Email 로 UserName 등록
	@Override
	public String getUsername() {
		return email;
	}

	public Long getUserId() {
		return userId;
	}

}
