package com.ada.library.api.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ada.library.api.model.BookModel;
import com.ada.library.api.model.dto.BookDTO;
import com.ada.library.domain.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "publisherId", target = "publisher.id")
	@Mapping(source = "genreId", target = "genre.id")
	Book parseToDomainObject(BookDTO bookDto);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "publisherId", target = "publisher.id")
	@Mapping(source = "genreId", target = "genre.id")
	Book copyToDomainObject(BookDTO bookDto, @MappingTarget Book book);

	BookModel parseToApiModel(Book book);
	
	List<BookModel> parseToCollectionApiModel(List<Book> book);
	
}
