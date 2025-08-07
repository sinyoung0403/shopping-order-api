package com.shoppingorderapi.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.shoppingorderapi.common.entity.BaseTimeEntity;

@Entity
@Table(name = "products")
@Getter
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int stock;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private String imageUrl;

	private Boolean isDeleted = false;

	@Builder
	public Product(String name, int price, int stock, String description, String imageUrl, Boolean isDeleted) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.imageUrl = imageUrl;
		this.isDeleted = isDeleted;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updatetPrice(int price) {
		this.price = price;
	}

	public void updateDescription(String description) {
		this.description = description;
	}

	public void updateImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
