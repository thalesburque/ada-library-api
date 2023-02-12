package com.ada.library.api.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ada.library.api.model.dto.UsuarioLoginDTO;
import com.ada.library.domain.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	List<UsuarioLoginDTO> parseListDTO(List<Usuario> clientes);
	List<Usuario> parseListEntity(List<UsuarioLoginDTO> clientes);
	@Mapping(target = "password", ignore=true)
	@Mapping(source = "perfil.id",target = "perfil")
	UsuarioLoginDTO parseDTO(Usuario cliente);
	@Mapping(target="authorities",ignore=true)
	@Mapping(source = "perfil",target = "perfil.id")
	Usuario parseEntity(UsuarioLoginDTO cliente);
}
