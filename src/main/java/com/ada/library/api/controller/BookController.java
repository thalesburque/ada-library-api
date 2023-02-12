package com.ada.library.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ada.library.api.model.BookModel;
import com.ada.library.api.model.dto.BookDTO;
import com.ada.library.api.model.mapper.BookMapper;
import com.ada.library.domain.model.Book;
import com.ada.library.domain.model.Genre;
import com.ada.library.domain.model.Publisher;
import com.ada.library.domain.repository.BookRepository;
import com.ada.library.domain.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookMapper mapper;

	@GetMapping
	public List<BookModel> list() {
		return mapper.parseToCollectionApiModel(bookRepository.findAll());
	}

	@GetMapping("/{bookId}")
	public BookModel search(@PathVariable Long bookId) {
		return mapper.parseToApiModel(bookService.findWithErrorHandling(bookId));
	}

	@GetMapping("/by-genre")
	public List<BookModel> searchByGenre(@RequestParam String genre) {
		return mapper.parseToCollectionApiModel(bookRepository.findByGenreName(genre));
	}

	@GetMapping("/by-publisher")
	public List<BookModel> searchByPublisher(@RequestParam String publisher) {
		return mapper.parseToCollectionApiModel(bookRepository.findByPublisherName(publisher));
	}

	@GetMapping("/by-name-or-isbn")
	public List<BookModel> searchByNameOrIsbn(@RequestParam String name, String isbn) {
		return mapper.parseToCollectionApiModel(bookRepository.findByNameOrIsbn(name, isbn));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookModel add(@RequestBody @Valid BookDTO bookDto) {

		Book book = mapper.parseToDomainObject(bookDto);

		return mapper.parseToApiModel(bookService.save(book));

	}

	@PutMapping("/{bookId}")
	public BookModel atualizar(@PathVariable Long bookId, @RequestBody @Valid BookDTO bookDto) {

		Book currentBook = bookService.findWithErrorHandling(bookId);

		// avoid jpa exception
		currentBook.setPublisher(new Publisher());
		currentBook.setGenre(new Genre());

		currentBook = mapper.copyToDomainObject(bookDto, currentBook);

		return mapper.parseToApiModel(bookService.save(currentBook));

	}

	@DeleteMapping("/{bookId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long bookId) {

		bookService.delete(bookId);

	}

}
