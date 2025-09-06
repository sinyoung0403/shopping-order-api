package com.shoppingorderapi.presentation.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponseDto {

	private final String accessToken;
	private final String refreshToken;

	public static SignInResponseDto of(String accessToken, String refreshToken) {
		return new SignInResponseDto(accessToken, refreshToken);
	}
}
