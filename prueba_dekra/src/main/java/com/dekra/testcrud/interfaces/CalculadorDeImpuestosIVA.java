package com.dekra.testcrud.interfaces;

public class CalculadorDeImpuestosIVA implements CalculadorDeImpuestos {
	
	private final Double IVA_TAX = 1.21;

	@Override
	public Double calculateTaxBasedOnPrice(Double price) {
		return price * IVA_TAX;
	}

}
