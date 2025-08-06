package com.shoppingorderapi.common.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shoppingorderapi.common.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 예외 처리
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException ex) {
		ErrorCode errorCode = ex.getErrorCode();

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(BaseResponse.error(
				errorCode.getMessage(),
				errorCode.getCode()
			));
	}

	/**
	 * 예상하지 못한 예외 처리
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<Void>> handleException(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(BaseResponse.error(
				ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
				ErrorCode.INTERNAL_SERVER_ERROR.getCode()
			));
	}
}
