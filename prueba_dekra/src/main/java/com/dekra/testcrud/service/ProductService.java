package com.dekra.testcrud.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jboss.jandex.Main;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dekra.testcrud.dto.ProductDTO;
import com.dekra.testcrud.entity.Product;
import com.dekra.testcrud.exception.ProductNotFoundException;
import com.dekra.testcrud.interfaces.CalculadorDeImpuestos;
import com.dekra.testcrud.interfaces.CalculadorDeImpuestosITBIS;
import com.dekra.testcrud.interfaces.CalculadorDeImpuestosIVA;
import com.dekra.testcrud.repository.ProductRepository;

@Service
public class ProductService {

	private static final String TAXES_PROPERTIES = "taxes.properties";
	private static final String TAX_PROPERTY = "tax";
	private static final String IVA_TAX = "IVA";

	@Autowired
	ProductRepository productRepository;

	private ModelMapper modelMapper;

	public ProductService() {
		modelMapper = new ModelMapper();
	}

	public List<ProductDTO> findAllProducts() {
		List<Product> productList = productRepository.findAll();
		return productList.stream().map(this::convertToProductDTO).collect(Collectors.toList());
	}

	public ProductDTO findProductById(Long id) {
		return convertToProductDTO(findProductByIdOrElseThrowException(id));
	}

	public ProductDTO createProduct(ProductDTO productDTO) {
		return convertToProductDTO(productRepository.save(convertToProductEntity(productDTO)));
	}

	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product productFound = findProductByIdOrElseThrowException(id);
		productFound.setName(productDTO.getName());
		productFound.setDescription(productDTO.getDescription());
		productFound.setPrice(productDTO.getPrice());
		return convertToProductDTO(productRepository.save(productFound));
	}

	public void deleteProductById(Long id) {
		findProductByIdOrElseThrowException(id);
		productRepository.deleteById(id);
	}

	private Product convertToProductEntity(ProductDTO productDTO) {
		return modelMapper.map(productDTO, Product.class);
	}

	private ProductDTO convertToProductDTO(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}

	private Product findProductByIdOrElseThrowException(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	public Double calculateTaxByProduct(ProductDTO productDTO) {
		CalculadorDeImpuestos taxCalculator = null;
		String taxType = getTaxTypeFromPropertyFile();
		if (taxType.equals(IVA_TAX)) {
			taxCalculator = new CalculadorDeImpuestosIVA();
		} else {
			taxCalculator = new CalculadorDeImpuestosITBIS();
		}
		return taxCalculator.calculateTaxBasedOnPrice(productDTO.getPrice());
	}

	private String getTaxTypeFromPropertyFile() {
		return loadProperties(TAXES_PROPERTIES).getProperty(TAX_PROPERTY);
	}

	private Properties loadProperties(String propertiesFilename) {
		Properties property = new Properties();
		ClassLoader loader = Main.class.getClassLoader();

		try (InputStream stream = loader.getResourceAsStream(propertiesFilename)) {
			property.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}
}
