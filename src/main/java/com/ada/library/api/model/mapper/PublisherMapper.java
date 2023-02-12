package com.ada.library.api.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ada.library.api.model.PublisherModel;
import com.ada.library.api.model.dto.PublisherDTO;
import com.ada.library.domain.model.Publisher;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
	
	@Mapping(target = "id", ignore = true)
	Publisher parseToDomainObject(PublisherDTO publisherDto);
	
	@Mapping(target = "id", ignore = true)
	Publisher copyToDomainObject(PublisherDTO publisherDto, @MappingTarget Publisher publisher);

	PublisherModel parseToApiModel(Publisher produto);
	
	List<PublisherModel> parseToCollectionApiModel(List<Publisher> publisher);
	
}
