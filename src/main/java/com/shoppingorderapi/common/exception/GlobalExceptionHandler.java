package com.shoppingorderapi.common.exception;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shoppingorderapi.common.response.BaseResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 예외 처리
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<BaseResponse<Void>> handleCustomException(CustomException ex) {
		log.error("Unexpected error occurred: ", ex);

		return ResponseEntity
			.status(ex.getErrorCode().getStatus())
			.body(BaseResponse.error(
				ex.getErrorCode().getMessage(),
				ex.getErrorCode().getCode()
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

	/**
	 * Security Role 보안 예외 처리
	 */

	@ExceptionHandler({
		org.springframework.security.access.AccessDeniedException.class,
		org.springframework.security.authorization.AuthorizationDeniedException.class
	})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public BaseResponse handleAccessDenied(Exception e) {
		return BaseResponse.error(
			ErrorCode.FORBIDDEN.getMessage(),
			ErrorCode.FORBIDDEN.getCode()
		);
	}

	/**
	 * Security Role 보안 예외 처리
	 */

	@ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public BaseResponse handleAuth(Exception e) {
		return BaseResponse.error(
			ErrorCode.UNAUTHORIZED.getMessage(),
			ErrorCode.UNAUTHORIZED.getCode()
		);
	}
}
