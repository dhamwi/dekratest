package com.dekra.testcrud.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3240739666825123740L;

	private Long productId;

	public ProductNotFoundException(Long productId) {
		this.productId = productId;
	}

	@Override
	public String getMessage() {
		return String.format("Product with ID '%s' NOT FOUND", this.productId);
	}

}
