package com.ada.library.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ada.library.domain.exception.EntityUsedException;
import com.ada.library.domain.exception.GenreNotFoundException;
import com.ada.library.domain.model.Genre;
import com.ada.library.domain.repository.GenreRepository;

import jakarta.transaction.Transactional;

@Service
public class GenreService {
	
	private static final String GENRE_HAS_BEEN_USED_MSG = "Genre with %d cannot be removed once it has been used";

	@Autowired
	private GenreRepository genreRepository;

	@Transactional
	public Genre save(Genre genre) {
		return genreRepository.save(genre);
	}
	
	@Transactional
	public void delete(Long genre) {
		try {
			genreRepository.deleteById(genre);
			genreRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GenreNotFoundException(genre);

		} catch (DataIntegrityViolationException e) {
			throw new EntityUsedException(String.format(GENRE_HAS_BEEN_USED_MSG, genre));
		}
	}

	public Genre findWithErrorHandling(Long genre) {
		return genreRepository.findById(genre).orElseThrow(() -> new GenreNotFoundException(genre));
	}

}
