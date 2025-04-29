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

    // ✅ CREATE a new Product - Protected by RateLimiter
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackCreateProduct")
    public Product createProduct(Product product) {
        return saveProduct(product);
    }

    // ✅ GET all Products - Protected by RateLimiter
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackGetAllProducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ GET Product by ID - Protected by RateLimiter
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackGetProductById")
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // ✅ SAVE product - Protected by RateLimiter
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackSaveProduct")
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ UPDATE product - Protected by RateLimiter
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "fallbackUpdateProduct")
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }).orElse(null);
    }

    // ✅ DELETE product (pas besoin de RateLimiter ici)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ✅ Fallback methods when RateLimiter is triggered

    public Product fallbackCreateProduct(Product product, Throwable t) {
        System.out.println("⚠️ fallbackCreateProduct: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    public List<Product> fallbackGetAllProducts(Throwable t) {
        System.out.println("⚠️ fallbackGetAllProducts: Rate limiter triggered - " + t.getMessage());
        return Collections.emptyList();
    }

    public Optional<Product> fallbackGetProductById(Long id, Throwable t) {
        System.out.println("⚠️ fallbackGetProductById: Rate limiter triggered for ID " + id + " - " + t.getMessage());
        return Optional.empty();
    }

    public Product fallbackSaveProduct(Product product, Throwable t) {
        System.out.println("⚠️ fallbackSaveProduct: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    public Product fallbackUpdateProduct(Long id, Product updatedProduct, Throwable t) {
        System.out.println("⚠️ fallbackUpdateProduct: Rate limiter triggered for ID " + id + " - " + t.getMessage());
        return null;
    }
}

