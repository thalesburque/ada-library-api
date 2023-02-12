package com.ada.library.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ada.library.domain.model.Book;
import com.ada.library.domain.repository.BookRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class BookRepositoryImpl implements BookRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Book> findByNameOrIsbn(String name, String isbn) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Book> criteria = builder.createQuery(Book.class);

		Root<Book> root = criteria.from(Book.class);

		var predicatos = new ArrayList<Predicate>();

		if (StringUtils.hasText(name)) {
			//predicatos.add(builder.equal(root.get("name"), name));
			
			predicatos.add(builder.equal(builder.upper(root.get("name")), name.toUpperCase()));
		}

		if (StringUtils.hasText(isbn)) {
			//predicatos.add(builder.equal(root.get("isbn"), isbn));
			
			predicatos.add(builder.equal(builder.upper(root.get("isbn")), isbn.toUpperCase()));
		}

		criteria.where(predicatos.toArray(new Predicate[0]));

		TypedQuery<Book> query = manager.createQuery(criteria);

		return query.getResultList();
	}

}
