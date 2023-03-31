package com.dekra.testcrud.interfaces;

public class CalculadorDeImpuestosITBIS implements CalculadorDeImpuestos {

	private final Double ITBIS_TAX = 1.18;
	
	@Override
	public Double calculateTaxBasedOnPrice(Double price) {
		return price * ITBIS_TAX;
	}

}
