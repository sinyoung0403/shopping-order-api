package com.shoppingorderapi.application.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.domain.user.UserRepository;
import com.shoppingorderapi.domain.user.dto.SignInRequestDto;
import com.shoppingorderapi.domain.user.dto.SignUpRequestDto;
import com.shoppingorderapi.domain.user.dto.SignUpResponseDto;

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
			throw new RuntimeException("이미 존재하는 이메일입니다.");
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
			.orElseThrow(() -> new RuntimeException(""));

		// 2. pwd 일치 여부 확인 - todo: 추후 Pwd Encoder 예정
		if (!findUser.getPassword().equals(signInRequestDto.getPassword())) {
			throw new RuntimeException();
		}

		// todo: 추후 토큰 일치 예정
	}

	@Transactional
	public void SignOut() {
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
