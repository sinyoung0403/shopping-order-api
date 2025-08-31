package com.shoppingorderapi.domain.order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import com.shoppingorderapi.domain.user.User;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private int totalItemCount;

	private int totalItemPrice;

	@Builder
	public Order(User user, OrderStatus status, int totalItemPrice) {
		this.user = user;
		this.status = status;
		this.totalItemPrice = totalItemPrice;
	}

	public void updateTotalItemPrice(int totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public void updateTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public void cancel() {
		this.status = OrderStatus.CANCELED;
	}
}
