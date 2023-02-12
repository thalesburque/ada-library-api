package com.ada.library.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ada.library.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRespository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);
	
	Optional<Usuario> findByEmail(String email);
	
}
