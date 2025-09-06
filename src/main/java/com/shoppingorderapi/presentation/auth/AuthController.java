package com.shoppingorderapi.presentation.auth;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.auth.AuthService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.presentation.dto.auth.request.SignInRequestDto;
import com.shoppingorderapi.presentation.dto.auth.request.SignUpRequestDto;
import com.shoppingorderapi.presentation.dto.auth.response.SignInResponseDto;
import com.shoppingorderapi.presentation.dto.auth.response.SignUpResponseDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<BaseResponse<SignUpResponseDto>> signUpUser(
		@Valid @RequestBody SignUpRequestDto signUpRequestDto
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(BaseResponse.success(authService.signUpUser(signUpRequestDto)));
	}

	@PostMapping("/login")
	public ResponseEntity<BaseResponse<SignInResponseDto>> login(
		@Valid @RequestBody SignInRequestDto signInRequestDto
	) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(authService.signIn(signInRequestDto)));
	}

	@PostMapping("/logout")
	public ResponseEntity<BaseResponse<Void>> logout() {
		// TODO: 로그아웃 로직 구현 필요
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}

	@PostMapping("/owners/signup")
	public ResponseEntity<BaseResponse<Void>> signUpOwner() {
		// TODO: 회원가입 - Owner 전용 구현 필요
		return ResponseEntity.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
