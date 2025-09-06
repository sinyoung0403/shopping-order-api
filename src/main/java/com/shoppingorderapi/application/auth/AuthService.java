package com.shoppingorderapi.application.auth;

import lombok.RequiredArgsConstructor;

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

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

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
	public void signOut() {
		// todo: 블랙 리스트 로직 구현 예정
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
