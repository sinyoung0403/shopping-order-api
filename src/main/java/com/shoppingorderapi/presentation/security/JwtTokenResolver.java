package com.shoppingorderapi.presentation.security;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * JWT TOKEN 의 Bearer 부분을 제외 시켜주는 Component
 */
@Component
public class JwtTokenResolver {
	private static final String AUTH_PREFIX = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	public Optional<String> resolve(HttpServletRequest req) {
		String header = req.getHeader(AUTH_PREFIX);
		if (header == null || !header.startsWith(BEARER_PREFIX)) {
			return Optional.empty();
		}
		return Optional.of(header.substring(BEARER_PREFIX.length()).trim());
	}
}
