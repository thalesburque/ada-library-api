package com.ada.library.domain.exception;

public class BookNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1L;
	
	public BookNotFoundException(String msg) {
		super(msg);
	}
	
	public BookNotFoundException(Long publisherId) {
		this(String.format("Book with id %d was not found", publisherId));
	}


}
