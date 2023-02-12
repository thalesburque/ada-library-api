package com.ada.library.domain.exception;

public class GenreNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1L;
	
	public GenreNotFoundException(String msg) {
		super(msg);
	}
	
	public GenreNotFoundException(Long publisherId) {
		this(String.format("Genre with id %d was not found", publisherId));
	}


}
