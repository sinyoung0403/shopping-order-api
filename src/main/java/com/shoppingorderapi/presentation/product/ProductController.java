package com.shoppingorderapi.presentation.product;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingorderapi.application.product.ProductService;
import com.shoppingorderapi.application.product.query.FindAllProductQueryDto;
import com.shoppingorderapi.application.product.query.FindProductQueryDto;
import com.shoppingorderapi.common.response.BaseResponse;
import com.shoppingorderapi.common.response.PageResponse;
import com.shoppingorderapi.presentation.dto.product.request.CreateProductRequestDto;
import com.shoppingorderapi.presentation.dto.product.request.UpdateProductRequestDto;
import com.shoppingorderapi.presentation.dto.product.response.CreateProductResponseDto;

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

	@GetMapping("/product/{productId}")
	public ResponseEntity<BaseResponse<FindProductQueryDto>> findProduct(
		@PathVariable Long productId
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(productService.getProduct(productId)));
	}

	@GetMapping("/product")
	public ResponseEntity<BaseResponse<PageResponse<FindAllProductQueryDto>>> findAllProduct(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(PageResponse.of(productService.getAllProduct(page, size))));
	}

	@PatchMapping("/product/{productId}")
	public ResponseEntity<BaseResponse<Void>> updateProduct(
		@PathVariable Long productId,
		@Valid @RequestBody UpdateProductRequestDto updateProductRequestDto
	) {
		productService.updateProduct(productId, updateProductRequestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<BaseResponse<Void>> deleteProduct(
		@PathVariable Long productId
	) {
		productService.deleteProduct(productId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(BaseResponse.success(null));
	}
}
