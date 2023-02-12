package com.ada.library.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ada.library.api.model.PublisherModel;
import com.ada.library.api.model.dto.PublisherDTO;
import com.ada.library.api.model.mapper.PublisherMapper;
import com.ada.library.domain.model.Publisher;
import com.ada.library.domain.repository.PublisherRepository;
import com.ada.library.domain.service.PublisherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
	
	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private PublisherMapper mapper;


	@GetMapping
	public List<PublisherModel> list() {
		return mapper.parseToCollectionApiModel(publisherRepository.findAll());
	}

	
	@GetMapping("/{publisherId}")
	public PublisherModel search(@PathVariable Long publisherId) { 
		return mapper.parseToApiModel(publisherService.findWithErrorHandling(publisherId));
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PublisherModel add(@RequestBody @Valid PublisherDTO publisherDto) {

		Publisher publisher = mapper.parseToDomainObject(publisherDto);
		
		return mapper.parseToApiModel(publisherService.save(publisher));

	}

	
	@PutMapping("/{publisherId}")
	public PublisherModel atualizar(@PathVariable Long publisherId, @RequestBody @Valid PublisherDTO publisherDto) {

		Publisher currentPublisher =  publisherService.findWithErrorHandling(publisherId);

		currentPublisher = mapper.copyToDomainObject(publisherDto, currentPublisher);

		return mapper.parseToApiModel(publisherService.save(currentPublisher));

	}

	@DeleteMapping("/{publisherId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long publisherId) {

		publisherService.delete(publisherId);

	}

}
