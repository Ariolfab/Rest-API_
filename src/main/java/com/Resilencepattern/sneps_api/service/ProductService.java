package com.Resilencepattern.sneps_api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Resilencepattern.sneps_api.model.Product;
import com.Resilencepattern.sneps_api.repository.ProductRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackGetAllProducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackGetProductById")
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ✅ Méthodes fallback
    public List<Product> fallbackGetAllProducts(Throwable t) {
        System.out.println("⚠️ fallbackGetAllProducts: Rate limiter triggered - " + t.getMessage());
        return Collections.emptyList();
    }

    public Optional<Product> fallbackGetProductById(Long id, Throwable t) {
        System.out.println("⚠️ fallbackGetProductById: Rate limiter triggered for ID " + id + " - " + t.getMessage());
        return Optional.empty();
    }
}

