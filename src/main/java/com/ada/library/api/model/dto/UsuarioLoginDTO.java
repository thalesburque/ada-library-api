package com.ada.library.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDTO extends UsuarioDTO{
	
		private String username;
		private String password;
}
