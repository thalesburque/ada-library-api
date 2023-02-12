package com.ada.library.domain.repository;

import java.util.List;

import com.ada.library.domain.model.Book;

public interface BookRepositoryQueries {

	List<Book> findByNameOrIsbn(String name, String isbn);
	
}
