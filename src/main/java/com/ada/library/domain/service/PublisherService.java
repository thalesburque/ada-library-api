package com.ada.library.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ada.library.domain.exception.EntityUsedException;
import com.ada.library.domain.exception.PublisherNotFoundException;
import com.ada.library.domain.model.Publisher;
import com.ada.library.domain.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@Service
public class PublisherService {
	
	private static final String PUBLISHER_HAS_BEEN_USED_MSG = "Publisher with %d cannot be removed once it has been used";

	@Autowired
	private PublisherRepository publisherRepository;

	@Transactional
	public Publisher save(Publisher publisher) {
		return publisherRepository.save(publisher);
	}
	
	@Transactional
	public void delete(Long publisherId) {
		try {
			publisherRepository.deleteById(publisherId);
			publisherRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PublisherNotFoundException(publisherId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityUsedException(String.format(PUBLISHER_HAS_BEEN_USED_MSG, publisherId));
		}
	}

	public Publisher findWithErrorHandling(Long publisherId) {
		return publisherRepository.findById(publisherId).orElseThrow(() -> new PublisherNotFoundException(publisherId));
	}

}
