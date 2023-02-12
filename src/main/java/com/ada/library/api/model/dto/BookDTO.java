package com.ada.library.api.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

	@NotNull
	private String name;
	
	@NotEmpty()
	@Size(max = 13)
	private String isbn;
	
	@NotNull
	private Long publisherId;
	
	@NotNull
	private Long genreId;

}
