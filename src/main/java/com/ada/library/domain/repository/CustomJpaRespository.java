package com.ada.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomJpaRespository<T, ID> extends JpaRepository<T, ID> {
	
	void detach(T entity);
	
}
