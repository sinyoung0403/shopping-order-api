package com.shoppingorderapi.application.product;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.domain.product.ProductRepository;
import com.shoppingorderapi.domain.product.dto.CreateProductRequest;
import com.shoppingorderapi.domain.product.dto.CreateProductResponse;
import com.shoppingorderapi.domain.product.dto.request.CreateProductRequestDto;
import com.shoppingorderapi.domain.product.dto.response.CreateProductResponseDto;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	/**
	 * 상품의 생성
	 *
	 * @param createProductRequest 상품 생성 요청 정보
	 * @return 생성된 상품의 ID
	 */
	@Transactional
	public CreateProductResponseDto createProduct(CreateProductRequestDto createProductRequest) {
		// 1. TODO: 유저 여부 확인 - OWNER 확인

		// 2. Product name 중복 여부 확인
		if (!productRepository.existsByName(createProductRequest.getName())) {
			throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
		}

		// 3. Product Entity 생성
		Product product = Product.builder()
			.name(createProductRequest.getName())
			.price(createProductRequest.getPrice())
			.stock(createProductRequest.getPrice())
			.description(createProductRequest.getDescription())
			.imageUrl(createProductRequest.getImageUrl())
			.isDeleted(false)
			.build();

		// 4. Product 저장
		productRepository.save(product);

		// 5. 응답 반환
		return CreateProductResponseDto.of(product.getId());
	}


}
