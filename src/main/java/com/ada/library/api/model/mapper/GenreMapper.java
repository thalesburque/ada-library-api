package com.ada.library.api.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ada.library.api.model.GenreModel;
import com.ada.library.api.model.dto.GenreDTO;
import com.ada.library.domain.model.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {
	
	@Mapping(target = "id", ignore = true)
	Genre parseToDomainObject(GenreDTO GenreDto);
	
	@Mapping(target = "id", ignore = true)
	Genre copyToDomainObject(GenreDTO GenreDto, @MappingTarget Genre Genre);

	GenreModel parseToApiModel(Genre Genre);
	
	List<GenreModel> parseToCollectionApiModel(List<Genre> Genre);
	
}
