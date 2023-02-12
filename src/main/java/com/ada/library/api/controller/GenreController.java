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

import com.ada.library.api.model.GenreModel;
import com.ada.library.api.model.dto.GenreDTO;
import com.ada.library.api.model.mapper.GenreMapper;
import com.ada.library.domain.model.Genre;
import com.ada.library.domain.repository.GenreRepository;
import com.ada.library.domain.service.GenreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genres")
public class GenreController {
	
	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private GenreService genreService;
	
	@Autowired
	private GenreMapper mapper;


	@GetMapping
	public List<GenreModel> list() {
		return mapper.parseToCollectionApiModel(genreRepository.findAll());
	}

	
	@GetMapping("/{genreId}")
	public GenreModel search(@PathVariable Long genreId) { 
		return mapper.parseToApiModel(genreService.findWithErrorHandling(genreId));
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GenreModel add(@RequestBody @Valid GenreDTO genreDto) {

		Genre genre = mapper.parseToDomainObject(genreDto);
		
		return mapper.parseToApiModel(genreService.save(genre));

	}

	
	@PutMapping("/{genreId}")
	public GenreModel atualizar(@PathVariable Long genreId, @RequestBody @Valid GenreDTO genreDto) {

		Genre currentGenre =  genreService.findWithErrorHandling(genreId);

		currentGenre = mapper.copyToDomainObject(genreDto, currentGenre);

		return mapper.parseToApiModel(genreService.save(currentGenre));

	}

	@DeleteMapping("/{genreId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long genreId) {

		genreService.delete(genreId);

	}

}
