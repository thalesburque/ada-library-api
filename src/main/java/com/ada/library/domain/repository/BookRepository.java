package com.ada.library.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ada.library.domain.model.Book;

public interface BookRepository extends BookRepositoryQueries ,JpaRepository<Book, Long>{

	@Query("select b from Book b left join fetch genre g left join fetch publisher p where b.genre.name = :genre")
	List<Book> findByGenreName(String genre);
	
	@Query("select b from Book b left join fetch genre g left join fetch publisher p where b.publisher.name = :publisher")
	List<Book> findByPublisherName(String publisher);
	
}
