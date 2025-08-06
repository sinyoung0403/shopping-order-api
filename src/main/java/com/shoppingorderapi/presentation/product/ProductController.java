package com.shoppingorderapi.presentation.product;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.product.ProductService;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.domain.product.dto.CreateProductRequest;
import com.shoppingorderapi.domain.product.dto.CreateProductResponse;
import com.shoppingorderapi.domain.product.dto.request.CreateProductRequestDto;
import com.shoppingorderapi.domain.product.dto.response.CreateProductResponseDto;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * 상품의 생성
	 *
	 * @param createProductRequest 상품 생성 요청 정보
	 * @return 생성된 상품 정보와 함께 201 Created 응답
	 */
	@PostMapping("/product")
	public ResponseEntity<BaseResponse<CreateProductResponseDto>> createProduct(
		@Valid @RequestBody CreateProductRequestDto createProductRequest
	) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(BaseResponse.success(productService.createProduct(createProductRequest)));
	}
}
