package com.Resilencepattern.sneps_api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.Resilencepattern.sneps_api.model.Supplier;
import com.Resilencepattern.sneps_api.repository.SupplierRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // ✅ GET all suppliers - Protected by RateLimiter
    @RateLimiter(name = "supplierRateLimiter", fallbackMethod = "fallbackGetAllSuppliers")
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // ✅ GET supplier by ID - Protected by RateLimiter
    @RateLimiter(name = "supplierRateLimiter", fallbackMethod = "fallbackGetSupplierById")
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    // ✅ POST create a new supplier - Protected by RateLimiter
    @RateLimiter(name = "supplierRateLimiter", fallbackMethod = "fallbackSaveSupplier")
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // ✅ PUT update supplier - Protected by RateLimiter
    @RateLimiter(name = "supplierRateLimiter", fallbackMethod = "fallbackUpdateSupplier")
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        return supplierRepository.findById(id).map(supplier -> {
            supplier.setName(updatedSupplier.getName());
            supplier.setContactInfo(updatedSupplier.getContactInfo());
            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    // ✅ DELETE supplier - Protected by RateLimiter
    @RateLimiter(name = "supplierRateLimiter", fallbackMethod = "fallbackDeleteSupplier")
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    // ✅ Fallback methods when RateLimiter is triggered

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public List<Supplier> fallbackGetAllSuppliers(Throwable t) {
        System.out.println("⚠️ fallbackGetAllSuppliers: Rate limiter triggered - " + t.getMessage());
        return Collections.emptyList();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Optional<Supplier> fallbackGetSupplierById(Long id, Throwable t) {
        System.out.println("⚠️ fallbackGetSupplierById: Rate limiter triggered - " + t.getMessage());
        return Optional.empty();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Supplier fallbackSaveSupplier(Supplier supplier, Throwable t) {
        System.out.println("⚠️ fallbackSaveSupplier: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Supplier fallbackUpdateSupplier(Long id, Supplier updatedSupplier, Throwable t) {
        System.out.println("⚠️ fallbackUpdateSupplier: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void fallbackDeleteSupplier(Long id, Throwable t) {
        System.out.println("⚠️ fallbackDeleteSupplier: Rate limiter triggered - " + t.getMessage());
    }
}
