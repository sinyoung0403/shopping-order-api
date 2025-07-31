package com.shoppingorderapi.presentation.auth;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.user.AuthService;
import com.shoppingorderapi.domain.user.dto.SignInRequestDto;
import com.shoppingorderapi.domain.user.dto.SignUpRequestDto;
import com.shoppingorderapi.domain.user.dto.SignUpResponseDto;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/users/signup")
	public ResponseEntity<SignUpResponseDto> signUpUser(
		@Valid @RequestBody SignUpRequestDto signUpRequestDto
	) {
		return ResponseEntity.ok(authService.signUpUser(signUpRequestDto));
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(
		@Valid @RequestBody SignInRequestDto signInRequestDto
	) {
		authService.signIn(signInRequestDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		return null;
	}

	@PostMapping("/owners/signup")
	public ResponseEntity<Void> signUpOwner() {
		return null;
	}
}
