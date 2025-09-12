package com.shoppingorderapi.application.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.infra.persistence.jpa.UserRepository;
import com.shoppingorderapi.infra.security.JwtTokenProvider;
import com.shoppingorderapi.presentation.dto.auth.request.SignInRequestDto;
import com.shoppingorderapi.presentation.dto.auth.request.SignUpRequestDto;
import com.shoppingorderapi.presentation.dto.auth.response.SignInResponseDto;
import com.shoppingorderapi.presentation.dto.auth.response.SignUpResponseDto;
import com.shoppingorderapi.presentation.security.JwtTokenResolver;

import io.jsonwebtoken.Claims;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenResolver jwtTokenResolver;
	private final RedisTemplate redisTemplate;

	@Transactional
	public SignUpResponseDto signUpUser(
		SignUpRequestDto dto
	) {
		// 1) 중복 조회
		userRepository.findByEmail(dto.getEmail())
			.ifPresent(user -> {
				throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
			});

		// 2) 패스워드 암호화
		String encodedPassword = passwordEncoder.encode(dto.getPassword());

		// 3) 유저 생성
		User user = new User(
			dto.getEmail(),
			encodedPassword,
			dto.getAddress()
		);

		// 4) 권한 설정
		user.assignUserRole();

		// 5) 유저 저장
		userRepository.save(user);

		// 6) DTO 반환
		return SignUpResponseDto.of(user.getId());
	}

	@Transactional
	public SignInResponseDto signIn(
		SignInRequestDto dto
	) {
		// 1) User Email 일치 여부 확인
		User findUser = userRepository.findByEmail(dto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

		// 2) pwd 일치 여부 확인
		if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
			throw new CustomException(ErrorCode.LOGIN_FAILED);
		}

		// 3) Token 발급
		String accessToken = jwtTokenProvider.generateAccessToken(findUser.getId(), findUser.getEmail(),
			findUser.getRole());
		String refreshToken = jwtTokenProvider.generateRefreshToken(findUser.getId(), findUser.getEmail(),
			findUser.getRole());

		// TODO: 4) Refresh Token 저장

		// 5) DTO 반환
		return SignInResponseDto.of(accessToken, refreshToken);
	}

	@Transactional
	public void logout(HttpServletRequest httpServletRequest) {
		// 1) HttpRequest 에서 Jwt 를 가져온다.
		String jwt = jwtTokenResolver.resolve(httpServletRequest).orElseThrow(
			() -> new CustomException(ErrorCode.UNAUTHORIZED)
		);

		// 2) 토큰 속의 Claims 을 가져온다.
		Claims claims = jwtTokenProvider.getClaims(jwt);

		// 3) Cliams 속의 Duration 을 가져와 토큰의 만료시간을 구한다.
		Duration expiredDuration = Duration.between(Instant.now(), claims.getExpiration().toInstant());

		// 4) 최소 TTL 보장
		long ttlSeconds = Math.max(1, expiredDuration.getSeconds()); // 최소 1초 보장

		// 5) Redis 에 저장하기
		redisTemplate.opsForValue().set("bl:" + jwt, "logout", ttlSeconds, TimeUnit.SECONDS);
	}

	@Transactional
	public SignUpResponseDto signUpOwner(
		SignUpRequestDto signUpRequestDto
	) {
		User user = new User(
			signUpRequestDto.getEmail(),
			signUpRequestDto.getPassword(),
			signUpRequestDto.getAddress()
		);

		user.assignOwnerRole();

		userRepository.save(user);

		return SignUpResponseDto.of(user.getId());
	}
}
