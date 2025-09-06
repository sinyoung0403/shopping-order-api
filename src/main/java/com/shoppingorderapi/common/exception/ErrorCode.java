package com.shoppingorderapi.common.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	/**
	 * 인증/인가 관련
	 */
	UNAUTHORIZED("UNAUTHORIZED", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
	FORBIDDEN("FORBIDDEN", "권한이 없습니다.", HttpStatus.FORBIDDEN),
	LOGIN_FAILED("LOGIN_FAILED", "로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED),
	TOKEN_INVALID("TOKEN_INVALID", "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),

	/**
	 * 유저 관련
	 */
	USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
	DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),

	/**
	 * Product 관련
	 */
	DUPLICATE_PRODUCT("DUPLICATE_PRODUCT", "이미 사용 중인 상품명입니다.", HttpStatus.CONFLICT),

	/**
	 * Cart 관련
	 */
	DUPLICATE_CART("DUPLICATE_CART", "이미 장바구니가 존재합니다.", HttpStatus.CONFLICT),
	CART_EMPTY("CART_EMPTY", "장바구니에 담긴 상품이 없습니다.", HttpStatus.BAD_REQUEST),

	/**
	 * 기타
	 */
	NOT_FOUND("NOT_FOUND", "존재하지 않습니다.", HttpStatus.NOT_FOUND),
	INVALID_INPUT("INVALID_INPUT", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String code;        // 비즈니스 코드 (프론트 대응용)
	private final String message;     // 사용자에게 보여줄 메시지
	private final HttpStatus status;  // HTTP 상태 코드

	ErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
