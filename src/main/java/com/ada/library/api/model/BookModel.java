package com.ada.library.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookModel {

	private Long id;

	private String name;

	private String isbn;
	
	private PublisherModel publisher;
	
	private GenreModel genre;
	
}
