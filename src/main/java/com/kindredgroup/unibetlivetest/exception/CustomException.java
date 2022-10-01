package com.kindredgroup.unibetlivetest.exception;

import com.kindredgroup.unibetlivetest.types.ExceptionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ExceptionType exception;
	private final String message;

	public CustomException(String message, ExceptionType exception) {
		this.message = message;
		this.exception = exception;
	}

}
