package com.temantani.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;

import com.temantani.security.service.JwtService;

import lombok.extern.slf4j.Slf4j;

// This class is our AuthenticationFilter ?
// We do not use Spring Boot Security Default AuthenticationFilter
// @Configuration
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String JWT_HEADER_PREFIX = "Bearer ";
		final String authHeader = request.getHeader("Authorization");
		final String subject;

		log.info("Filtering JWT");

		if (authHeader == null || !authHeader.startsWith(JWT_HEADER_PREFIX)) {
			log.warn("[JwtAuthFilter] No token provided");
			filterChain.doFilter(request, response);
			return;
		}

		final String token = authHeader.substring(JWT_HEADER_PREFIX.length());

		try {
			subject = jwtService.extractSubject(token);
		} catch (Exception e) {
			log.error("[JwtAuthFilter] Unable to parse token: {}", e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}

		if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			List<String> roles = jwtService.extractRoles(token);
			UserDetails userDetails = User.builder().username(subject).password("")
					.roles(roles.toArray(new String[0])).build();

			if (jwtService.isTokenValid(token, userDetails)) {
				log.info("[JwtAuthFilter] Roles: {}",
						userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" ")));

				UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.authenticated(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
