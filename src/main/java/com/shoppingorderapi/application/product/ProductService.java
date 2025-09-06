package com.shoppingorderapi.application.product;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.infra.persistence.jpa.ProductRepository;
import com.shoppingorderapi.presentation.dto.product.request.CreateProductRequestDto;
import com.shoppingorderapi.presentation.dto.product.request.UpdateProductRequestDto;
import com.shoppingorderapi.presentation.dto.product.response.CreateProductResponseDto;
import com.shoppingorderapi.presentation.dto.product.response.FindAllProductResponseDto;
import com.shoppingorderapi.presentation.dto.product.response.FindProductResponseDto;

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
		if (productRepository.existsByName(createProductRequest.getName())) {
			throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
		}

		// 3. Product Entity 생성
		Product product = Product.builder()
			.name(createProductRequest.getName())
			.price(createProductRequest.getPrice())
			.stock(createProductRequest.getStock())
			.description(createProductRequest.getDescription())
			.imageUrl(createProductRequest.getImageUrl())
			.isDeleted(false)
			.build();

		// 4. Product 저장
		productRepository.save(product);

		// 5. 응답 반환
		return CreateProductResponseDto.of(product.getId());
	}

	@Transactional(readOnly = true)
	public FindProductResponseDto getProduct(Long productId) {
		return productRepository.findProductWithId(productId);
	}

	@Transactional(readOnly = true)
	public Page<FindAllProductResponseDto> getAllProduct(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("createdAt")));
		return productRepository.findAllProduct(pageable);
	}

	@Transactional
	public void updateProduct(Long productId, UpdateProductRequestDto updateProductRequestDto) {
		// 1. Entity 조회
		Product product = productRepository.findByIdOrElseThrow(productId);

		// 2. 상품명 중복 검사 및 변경
		if (updateProductRequestDto.getName() != null) {
			if (!product.getName().equals(updateProductRequestDto.getName()) &&
				productRepository.existsByName(updateProductRequestDto.getName())) {
				throw new CustomException(ErrorCode.DUPLICATE_PRODUCT);
			}
			product.updateName(updateProductRequestDto.getName());
		}

		// 3. 가격 변경
		if (updateProductRequestDto.getPrice() != null) {
			product.updatePrice(updateProductRequestDto.getPrice());
		}

		// 4. 설명 변경
		if (updateProductRequestDto.getDescription() != null) {
			product.updateDescription(updateProductRequestDto.getDescription());
		}

		// 5. 이미지 URL 변경
		if (updateProductRequestDto.getImageUrl() != null) {
			product.updateImageUrl(updateProductRequestDto.getImageUrl());
		}
	}

	@Transactional
	public void deleteProduct(Long productId) {
		// 1. Entity 조회
		Product product = productRepository.findByIdOrElseThrow(productId);

		// 2. 삭제
		product.delete();
	}
}
