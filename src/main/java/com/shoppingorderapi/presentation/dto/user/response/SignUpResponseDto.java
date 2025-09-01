package com.shoppingorderapi.presentation.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
	private Long userId;

	public static SignUpResponseDto of(Long userId) {
		return new SignUpResponseDto(userId);
	}
}
