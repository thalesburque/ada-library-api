package com.ada.library.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.library.api.model.dto.TokenDTO;
import com.ada.library.api.model.dto.UsuarioDTO;
import com.ada.library.api.model.dto.UsuarioLoginDTO;
import com.ada.library.domain.exception.BusinessException;
import com.ada.library.domain.exception.EntityUsedException;
import com.ada.library.domain.exception.UserNotFoundException;
import com.ada.library.domain.model.Usuario;
import com.ada.library.domain.repository.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class UsuarioService {

	private static final String MSG_USUARIO_EM_USO = "Usuario de codigo %d nao pode ser removido, pois esta em uso";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Transactional
	public Usuario save(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new BusinessException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void delete(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
			
		} catch(EmptyResultDataAccessException e) {
			throw new UserNotFoundException(usuarioId);
		} catch(DataIntegrityViolationException e) {
			throw new EntityUsedException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		} 
	}
	
	public Usuario findWithErrorHandling(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UserNotFoundException(usuarioId));
	}
	
	public TokenDTO atualizarToken(String refreshToken) {
		
		if(jwtService.validRefreshToken(refreshToken)) {
			String username = jwtService.getUsernameByRefreshToken(refreshToken);
			
			return buildTokenDTO(username,null);
		}
		
		throw new ExpiredJwtException(null, null,"Refresh token foi expirado.");
	}
	
	public TokenDTO logar(UsuarioLoginDTO usuarioLoginDTO) throws AuthenticationException,UsernameNotFoundException {
		
		UsernamePasswordAuthenticationToken autentication = 
				new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(),usuarioLoginDTO.getPassword());
		
		authenticationManager.authenticate(autentication);
		
		Usuario usuario = (Usuario) authService.loadUserByUsername(usuarioLoginDTO.getUsername());
		
		return buildTokenDTO(usuario.getUsername(),usuario);
	}
	
	private TokenDTO buildTokenDTO(String username,Usuario usuario) {
		
		UsuarioDTO usuarioDTO = null;
		if(usuario!=null) {
			usuarioDTO = new UsuarioDTO();
			usuarioDTO.setId(usuario.getId());
			usuarioDTO.setNome(usuario.getNome());
			usuarioDTO.setEmail(usuario.getEmail());
			usuarioDTO.setPerfil(usuario.getPerfil().getId());
		}
		
		String token = jwtService.generateToken(username);
		String refreshToken = jwtService.generateRefreshToken(username);
		return TokenDTO.builder()
				.token(token)
				.refreshToken(refreshToken)
				.type("Bearer")
				.user(usuarioDTO)
				.build();
	}
}
