package com.shoppingorderapi.presentation.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shoppingorderapi.domain.user.UserRole;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class)
			&& parameter.getParameterType().equals(AuthUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null ||
			"anonymousUser".equals(authentication.getPrincipal())) {
			return null;
		}

		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		String role = principal.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");


		return new AuthUser(principal.getUserId(), principal.getUsername(), UserRole.of(role));
	}
}
