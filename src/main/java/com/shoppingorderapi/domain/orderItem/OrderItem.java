package com.shoppingorderapi.domain.orderItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.shoppingorderapi.domain.order.Order;
import com.shoppingorderapi.domain.product.Product;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE order_items SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false, length = 200)
	private String productNameSnapshot;

	@Column(nullable = false)
	private int unitPriceSnapshot;

	@Column(nullable = false)
	private int quantity;

	/**
	 * 총액 계산
	 */
	@Column(nullable = false)
	private int lineTotal;

	private Boolean isDeleted = false;

	@Builder
	public OrderItem(Order order, Product product, String productNameSnapshot, int unitPriceSnapshot, int quantity,
		int lineTotal) {
		this.order = order;
		this.product = product;
		this.productNameSnapshot = productNameSnapshot;
		this.unitPriceSnapshot = unitPriceSnapshot;
		this.quantity = quantity;
		this.lineTotal = lineTotal;
		this.isDeleted = false;
	}

	public void delete() {
		this.isDeleted = true;
	}
}
