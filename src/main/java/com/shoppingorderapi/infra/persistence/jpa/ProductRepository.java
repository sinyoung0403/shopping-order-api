package com.shoppingorderapi.infra.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingorderapi.common.repository.BaseRepository;
import com.shoppingorderapi.domain.product.Product;
import com.shoppingorderapi.presentation.dto.product.response.FindAllProductResponseDto;
import com.shoppingorderapi.presentation.dto.product.response.FindProductResponseDto;

public interface ProductRepository extends BaseRepository<Product, Long> {

	/**
	 * 상품명 존재 여부
	 *
	 * @param name
	 * @return 상품명 존재 여부에 대한 True/False
	 */
	boolean existsByName(String name);

	@Query(
		"SELECT new com.shoppingorderapi.presentation.dto.product.response.FindProductResponseDto(p.name, p.price, p.stock, p.description, p.imageUrl, p.createdAt) "
			+ "FROM Product p WHERE p.id = :productId"
	)
	FindProductResponseDto findProductWithId(@Param("productId") Long productId);

	@Query(
		"SELECT new com.shoppingorderapi.presentation.dto.product.response.FindAllProductResponseDto(p.id, p.name, p.price, p.stock, p.imageUrl) "
			+ "FROM Product p "
	)
	Page<FindAllProductResponseDto> findAllProduct(Pageable pageable);

}
