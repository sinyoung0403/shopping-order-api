package com.shoppingorderapi.presentation.security;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * JWT TOKEN 의 Bearer 부분을 제외 시켜주는 Component
 */
@Component
public class JwtTokenResolver {
	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	public Optional<String> resolve(HttpServletRequest req) {
		// 1) Header 의 Authorization 가져오기
		String header = req.getHeader(AUTH_HEADER);

		// 2) Header null 인지 확인
		if (header == null) {
			return Optional.empty();
		}

		// 3) 공백 제거
		String value = header.trim();

		// 4) 대소문자 무시, value 의 0번째 문자열 부터 비교, 비교 대상 문자열,
		// 비교 대상 문자열의 0번째 문자열 부터 비교, 비교 대상 문자열 길이 만큼 비교 후 매치 안되면 빈값 반환
		if (!value.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
			return Optional.empty();
		}

		// 5) 만약 맞다면, 순수 토큰 값만 추출
		String token = value.substring(BEARER_PREFIX.length()).trim();

		// 6) 추출한 토큰 값이 비어있다면 빈값 반환, 아니라면 token 반환
		return token.isEmpty() ? Optional.empty() : Optional.of(token);
	}
}
