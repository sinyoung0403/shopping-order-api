package com.shoppingorderapi.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 성공 시 code 필드 생략 처리 됨. 실패 시 code 생략 처리.
public class BaseResponse<T> {
	private boolean success;
	private T data;
	private String message;
	private String code;

	// 성공 응답
	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse<>(true, data, "요청이 성공했습니다.", null);
	}

	public static <T> BaseResponse<T> success(T data, String message) {
		return new BaseResponse<>(true, data, message, null);
	}

	// 실패 응답
	public static <T> BaseResponse<T> error(String message, String code) {
		return new BaseResponse<>(false, null, message, code);
	}

}
