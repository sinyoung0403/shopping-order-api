package com.shoppingorderapi.domain.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

	private String email;

	private String password;

	private String address;
}
