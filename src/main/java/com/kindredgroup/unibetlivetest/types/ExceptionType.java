package com.kindredgroup.unibetlivetest.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionType {

	CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND), EVENT_NOT_FOUND(HttpStatus.NOT_FOUND),
	NON_AUTHORITATIVE_INFORMATION(HttpStatus.NON_AUTHORITATIVE_INFORMATION),
	SELECTION_NOT_FOUND(HttpStatus.BAD_REQUEST), SELECTION_CLOSED(HttpStatus.BAD_REQUEST),
	INSUFFUCIENT_BALANCE(HttpStatus.BAD_REQUEST), ODD_CHANGE(HttpStatus.BAD_REQUEST), CONFLICT(HttpStatus.CONFLICT);

	@Getter
	final HttpStatus status;

	ExceptionType(HttpStatus status) {
		this.status = status;
	}

}
