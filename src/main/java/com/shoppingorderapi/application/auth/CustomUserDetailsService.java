package com.shoppingorderapi.application.auth;

import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;
import com.shoppingorderapi.domain.user.User;
import com.shoppingorderapi.infra.persistence.jpa.UserRepository;
import com.shoppingorderapi.presentation.security.CustomUserDetails;

/**
 * 사용자 로드 → UserDetails 로 변환
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// 1) 유저 조회
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		// 2) 권한 매핑
		Collection<? extends GrantedAuthority> authorities =
			List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		// 3) CustomUserDetails 생성
		return CustomUserDetails.of(user.getId(), email, authorities);
	}
}
