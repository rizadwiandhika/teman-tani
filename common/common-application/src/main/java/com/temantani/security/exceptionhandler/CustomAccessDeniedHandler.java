package com.temantani.security.exceptionhandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("[CustomAccessDeniedHandler] error: {}", accessDeniedException.getMessage());

		response.setStatus(HttpStatus.FORBIDDEN.value());
		Map<String, Object> data = new HashMap<>();
		data.put(
				"timestamp",
				Calendar.getInstance().getTime());
		data.put(
				"exception",
				accessDeniedException.getMessage());
		data.put("from", "CustomAccessDeniedHandler");

		response.getOutputStream()
				.println(objectMapper.writeValueAsString(data));
	}

}
