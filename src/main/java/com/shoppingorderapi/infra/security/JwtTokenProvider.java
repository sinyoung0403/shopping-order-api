package com.shoppingorderapi.infra.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoppingorderapi.domain.user.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final String BEARER_PREFIX = "Bearer ";
	public final long ACCESS_TOKEN_EXPIRATION_MS = 1000L * 60 * 60; // 60분
	public final long REFRESH_TOKEN_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7; // 7일

	private Key key;

	public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey) {
		Assert.hasText(secretKey, "jwt.secret.key must be set");
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateAccessToken(Long userId, String email, UserRole role) {
		return generateToken(userId, email, role, ACCESS_TOKEN_EXPIRATION_MS);
	}

	public String generateRefreshToken(Long userId, String email, UserRole role) {
		return generateToken(userId, email, role, REFRESH_TOKEN_EXPIRATION_MS);
	}

	// Token 생성
	private String generateToken(Long userId, String email, UserRole role, long expirationMS) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationMS);

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(String.valueOf(userId))
				.claim("userId", userId)
				.claim("email", email)
				.claim("role", role.name())
				.setExpiration(expiryDate)
				.setIssuedAt(now)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	// Token 추출
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
