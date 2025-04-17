package com.Resilencepattern.sneps_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Resilencepattern.sneps_api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
