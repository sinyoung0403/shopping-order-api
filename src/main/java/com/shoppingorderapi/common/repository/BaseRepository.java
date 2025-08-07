package com.shoppingorderapi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.shoppingorderapi.common.exception.CustomException;
import com.shoppingorderapi.common.exception.ErrorCode;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

	default T findByIdOrElseThrow(ID id) {
		return findById(id).orElseThrow(() ->
			new CustomException(ErrorCode.NOT_FOUND, "요청하신 리소스를 찾을 수 없습니다. id = " + id)
		);
	}

}
