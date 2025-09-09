package com.shoppingorderapi.presentation.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	// Chain Filter Bean 등록
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				// Auth 경로와 Swagger 문서 경로 Permit
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

				// 경로별 권한 매칭
				.requestMatchers("/owner/**").hasAnyRole("OWNER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "OWNER")

				.anyRequest().authenticated()
			)

			// 세션을 STATELESS
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			// UsernamePasswordAuthenticationFilter 이전에 Jwt Filter 추가
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}

	// Password Encoder Bean 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
