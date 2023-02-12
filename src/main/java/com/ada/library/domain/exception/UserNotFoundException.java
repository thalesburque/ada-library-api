package com.ada.library.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
	
	public UserNotFoundException(Long userId) {
		this(String.format("User with id %d was not found", userId));
	}


}
