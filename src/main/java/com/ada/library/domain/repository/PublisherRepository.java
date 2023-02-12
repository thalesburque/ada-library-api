package com.ada.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.library.domain.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

}
