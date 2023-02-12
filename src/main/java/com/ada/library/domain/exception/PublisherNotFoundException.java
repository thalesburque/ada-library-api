package com.ada.library.domain.exception;

public class PublisherNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1L;
	
	public PublisherNotFoundException(String msg) {
		super(msg);
	}
	
	public PublisherNotFoundException(Long publisherId) {
		this(String.format("Publisher with id %d was not found", publisherId));
	}


}
