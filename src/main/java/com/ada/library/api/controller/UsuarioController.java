package com.ada.library.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ada.library.api.model.dto.TokenDTO;
import com.ada.library.api.model.dto.UsuarioLoginDTO;
import com.ada.library.api.model.mapper.UsuarioMapper;
import com.ada.library.domain.model.Usuario;
import com.ada.library.domain.repository.UsuarioRepository;
import com.ada.library.domain.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsuarioController {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	UsuarioMapper mapper;
	
	@GetMapping
	public List<UsuarioLoginDTO> list() {
		return mapper.parseListDTO(repository.findAll());
	}

	
	@GetMapping("/{usuarioId}")
	public UsuarioLoginDTO search(@PathVariable Long usuarioId) { 
		return mapper.parseDTO(service.findWithErrorHandling(usuarioId));
	}

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioLoginDTO add(@RequestBody @Valid UsuarioLoginDTO usuarioDto) {

		Usuario usuario = mapper.parseEntity(usuarioDto);
		
		return mapper.parseDTO(service.save(usuario));

	}

	
	@PutMapping("/{usuarioId}")
	public UsuarioLoginDTO update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioLoginDTO usuarioDto) {

		Usuario currentUser =  service.findWithErrorHandling(usuarioId);

		currentUser.setEmail(usuarioDto.getEmail());
		currentUser.setNome(usuarioDto.getNome());

		return mapper.parseDTO(service.save(currentUser));

	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {

		service.delete(usuarioId);

	}
	
	@PostMapping("/auth")
	public ResponseEntity<TokenDTO> logar(@RequestBody @Valid UsuarioLoginDTO usuario) {
		try {

            return ResponseEntity.ok(service.logar(usuario));

        }catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
	}
	
	@GetMapping("/auth/{refreshToken}")
	public ResponseEntity<TokenDTO> atualizarToken(@PathVariable("refreshToken") String refreshToken) {
		try {

            return ResponseEntity.ok(service.atualizarToken(refreshToken));

        }catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
	}
}
