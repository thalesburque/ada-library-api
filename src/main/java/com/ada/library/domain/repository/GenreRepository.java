package com.ada.library.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.library.domain.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

}
