package com.shoppingorderapi.presentation.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.infra.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenResolver jwtTokenResolver;
	private final RedisTemplate redisTemplate;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri.startsWith("/auth/")
			|| uri.startsWith("/swagger-ui/")
			|| uri.startsWith("/v3/api-docs")
			|| "OPTIONS".equalsIgnoreCase(request.getMethod()); // CORS preflight 스킵
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException, IOException {

		// 1) token 추출
		String token = jwtTokenResolver.resolve(request).orElse(null);

		// 2) Token 없을 시, 필터 통과
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// 3) Claims 추출 (검증실패는 예외발생)
			Claims claims = jwtTokenProvider.getClaims(token);

			// 4) 블랙리스트 확인
			if (isTokenBlacklisted(token)) {
				sendErrorResponse(response, ErrorCode.TOKEN_BLACKLISTED);
				return;
			}

			// 5) 인증 객체 세팅 후 체인 진행
			Authentication auth = buildAuthentication(claims);
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);

		} catch (JwtException e) {
			// 6) 검증 실패 시 에러 응답 후 종료
			sendErrorResponse(response, ErrorCode.TOKEN_INVALID);
			return;
		}

	}

	private Authentication buildAuthentication(Claims claims) {

		// 1) 유저 조회
		Long userId = Long.valueOf(claims.getSubject());

		// 2) Claims 속 Role 추출
		String email = claims.get("email", String.class);
		String role = claims.get("role", String.class);
		if (role == null || email == null)
			throw new JwtException("Claim missing");

		// 3) 유저 권한을 authorities 에 넣기.
		//	  이때, "ROLE_" Prefix 추가
		Collection<? extends GrantedAuthority> authorities =
			List.of(new SimpleGrantedAuthority("ROLE_" + role));

		// 4) UserDetails 생성
		CustomUserDetails principal = CustomUserDetails.of(userId, email, authorities);

		return new UsernamePasswordAuthenticationToken(principal, null, authorities);
	}

	/**
	 * AccessToken 이 Blacklist 에 있는지 검증
	 *
	 * @param accessToken
	 * @return boolean
	 */
	private boolean isTokenBlacklisted(String accessToken) {
		return redisTemplate.hasKey("bl:" + accessToken);
	}

	/**
	 * Filter Error
	 */
	public void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		response.setStatus(errorCode.getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String jsonErrorResponse = "{"
			+ "\"success\": false,"
			+ "\"message\": \"" + errorCode.getMessage() + "\","
			+ "\"code\": \"" + errorCode.getCode() + "\""
			+ "}";

		response.getWriter().write(jsonErrorResponse);
	}
}
