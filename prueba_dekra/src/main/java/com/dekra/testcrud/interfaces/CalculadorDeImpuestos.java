package com.dekra.testcrud.interfaces;

@FunctionalInterface
public interface CalculadorDeImpuestos {

	Double calculateTaxBasedOnPrice(Double price);
}
