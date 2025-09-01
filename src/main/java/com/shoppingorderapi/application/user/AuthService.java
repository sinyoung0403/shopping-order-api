package com.shoppingorderapi.application.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.infra.persistence.jpa.UserRepository;
import com.shoppingorderapi.presentation.dto.user.request.SignInRequestDto;
import com.shoppingorderapi.presentation.dto.user.request.SignUpRequestDto;
import com.shoppingorderapi.presentation.dto.user.response.SignUpResponseDto;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	@Transactional
	public SignUpResponseDto signUpUser(
		SignUpRequestDto signUpRequestDto
	) {
		userRepository.findByEmail(signUpRequestDto.getEmail())
			.ifPresent(user -> {
				throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
			});

		User user = new User(
			signUpRequestDto.getEmail(),
			signUpRequestDto.getPassword(),
			signUpRequestDto.getAddress()
		);

		user.assignUserRole();

		userRepository.save(user);

		return SignUpResponseDto.of(user.getId());
	}

	@Transactional
	public void signIn(
		SignInRequestDto signInRequestDto
	) {
		// 1. User Email 일치 여부 확인
		User findUser = userRepository.findByEmail(signInRequestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

		// 2. pwd 일치 여부 확인 - todo: 추후 Pwd Encoder 예정
		if (!findUser.getPassword().equals(signInRequestDto.getPassword())) {
			throw new CustomException(ErrorCode.LOGIN_FAILED);
		}

		// todo: 추후 토큰 일치 예정
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
