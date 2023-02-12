package com.ada.library.api.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherDTO {

	@NotEmpty()
	@Size(max = 255)
	private String name;

	@NotNull
	private String description;

}
