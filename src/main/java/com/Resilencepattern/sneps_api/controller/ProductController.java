package com.Resilencepattern.sneps_api.controller;

import com.Resilencepattern.sneps_api.model.Product;
import com.Resilencepattern.sneps_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET sneps.com/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET sneps.com/products/{id}
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // POST sneps.com/products
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // PUT sneps.com/products/{id}
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // DELETE (facultatif si tu veux l'ajouter)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // BONUS : GET /products?pricemin=x&pricemax=y
    @GetMapping(params = {"pricemin", "pricemax"})
    public List<Product> getProductsByPriceRange(@RequestParam Double pricemin, @RequestParam Double pricemax) {
        return productService.getAllProducts().stream()
                .filter(p -> p.getPrice() >= pricemin && p.getPrice() <= pricemax)
                .toList();
    }
}
