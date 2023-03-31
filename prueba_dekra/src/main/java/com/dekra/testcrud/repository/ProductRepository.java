package com.dekra.testcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dekra.testcrud.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
