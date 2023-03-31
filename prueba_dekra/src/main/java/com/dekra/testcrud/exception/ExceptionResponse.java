package com.dekra.testcrud.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

	private LocalDateTime localDateTime;
	private String message;
	private int responseCode;
	private String status;

	public ExceptionResponse() {
	}

	public ExceptionResponse(LocalDateTime localDateTime, String message, int responseCode, String status) {
		this.localDateTime = localDateTime;
		this.message = message;
		this.responseCode = responseCode;
		this.status = status;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}