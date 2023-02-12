package com.ada.library.core.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ada.library.domain.model.Usuario;
import com.ada.library.domain.service.AuthService;
import com.ada.library.domain.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenString = request.getHeader("Authorization");
		
		if(tokenString!=null) {
			tokenString = tokenString.replace("Bearer ","");
			if(jwtService.validToken(tokenString)) {
				String username = jwtService.getUsernameByToken(tokenString);
				
				if(!username.isBlank() && SecurityContextHolder.getContext().getAuthentication()==null) {
					Usuario usuario = (Usuario) authService.loadUserByUsername(username);
					
					UsernamePasswordAuthenticationToken autentication = 
							new UsernamePasswordAuthenticationToken(usuario.getUsername(),null,usuario.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(autentication);
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
