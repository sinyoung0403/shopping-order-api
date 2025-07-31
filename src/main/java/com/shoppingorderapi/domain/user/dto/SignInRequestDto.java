package com.shoppingorderapi.domain.user.dto;

import lombok.Getter;

@Getter
public class SignInRequestDto {
	private String email;
	private String password;
}
