package com.shoppingorderapi.domain.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.shoppingorderapi.common.entity.BaseTimeEntity;
import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.user.User;

/**
 * 장바구니 Entity
 * - Soft Deleted 를 적용하지 않음.
 */

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
		name = "user_id",
		nullable = false,
		unique = true
	)
	private User user;

	@Builder
	public Cart(User user) {
		if (user == null) throw new CustomException(ErrorCode.INVALID_INPUT);
		this.user = user;
	}
}
