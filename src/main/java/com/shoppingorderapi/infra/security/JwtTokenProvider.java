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

		return Jwts.builder()
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
		// 1) 토큰이 비어있지 않거나, token 문자열의 맨 앞 부분이 "Bearer "와 같나 확인
		String raw = token != null && token.regionMatches(
			true, // 대소문자 구문 X
			0,  // Token 의 0번째 인덱스부터 비교
			BEARER_PREFIX,  // 비교 대상 문자
			0, // 비교 대상 문자의 0번째 인덱스부터 비교
			BEARER_PREFIX.length()) // 비교 대상 문자열 길이 만큼 비교
			? token.substring(BEARER_PREFIX.length()).trim()
			: token;

		// 2) raw 토큰 속에서 Claims 획득
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(raw)
			.getBody();
	}
}
