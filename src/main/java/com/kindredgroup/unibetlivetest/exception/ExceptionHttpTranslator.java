package com.kindredgroup.unibetlivetest.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kindredgroup.unibetlivetest.dto.ExceptionDto;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHttpTranslator {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionDto> businessException(HttpServletRequest request, final CustomException e) {
		return new ResponseEntity<ExceptionDto>(new ExceptionDto().setErrormessage(e.getMessage()).setPath(request.getServletPath()),
				e.getException().getStatus());
	}

}
