package com.ada.library.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ada.library.domain.exception.BookNotFoundException;
import com.ada.library.domain.exception.EntityUsedException;
import com.ada.library.domain.model.Book;
import com.ada.library.domain.model.Genre;
import com.ada.library.domain.model.Publisher;
import com.ada.library.domain.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
	
	private static final String BOOK_HAS_BEEN_USED_MSG = "Book with %d cannot be removed once it has been used";

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private GenreService genreService;
	

	@Transactional
	public Book save(Book book) {
		
		Long publisherId = book.getPublisher().getId();
		Publisher publisher = publisherService.findWithErrorHandling(publisherId);
		book.setPublisher(publisher);
		
		Long genreId = book.getGenre().getId();
		Genre genre = genreService.findWithErrorHandling(genreId);
		book.setGenre(genre);
		
		
		return bookRepository.save(book);
	}
	
	@Transactional
	public void delete(Long bookId) {
		try {
			bookRepository.deleteById(bookId);
			bookRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new BookNotFoundException(bookId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityUsedException(String.format(BOOK_HAS_BEEN_USED_MSG, bookId));
		}
	}

	public Book findWithErrorHandling(Long bookId) {
		return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
	}

}
