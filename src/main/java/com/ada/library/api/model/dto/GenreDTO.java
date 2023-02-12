package com.ada.library.api.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {

	@NotEmpty()
	@Size(max = 100)
	private String name;

}
