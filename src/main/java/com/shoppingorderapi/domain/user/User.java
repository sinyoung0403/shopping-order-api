package com.shoppingorderapi.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.shoppingorderapi.common.entity.BaseTimeEntity;

@Entity
@Table(name = "users")
@Getter
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@NotBlank
	@Column(unique = true, nullable = false)
	private String email;

	@NotBlank
	@Column(nullable = false)
	private String password;

	private String address;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(nullable = false)
	private Boolean isDeleted = false;

	public User(String email, String password, String address) {
		this.email = email;
		this.password = password;
		this.address = address;
	}

	public void assignUserRole() {
		this.role = UserRole.USER;
	}

	public void assignOwnerRole() {
		this.role = UserRole.OWNER;
	}
}
