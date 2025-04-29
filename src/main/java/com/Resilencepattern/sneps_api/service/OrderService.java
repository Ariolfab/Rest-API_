package com.Resilencepattern.sneps_api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.Resilencepattern.sneps_api.model.Order;
import com.Resilencepattern.sneps_api.repository.OrderRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 📌 GET all orders - Protected by RateLimiter
     */
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "fallbackGetAllOrders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * 📌 GET order by ID - Protected by RateLimiter
     */
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "fallbackGetOrderById")
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * 📌 POST save a new order - Protected by RateLimiter
     */
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "fallbackSaveOrder")
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * 📌 PATCH update order status - Protected by RateLimiter
     */
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "fallbackUpdateOrderStatus")
    public Order updateOrderStatus(Long id, String newStatus) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }).orElse(null);
    }

    /**
     * 📌 DELETE order by ID - Protected by RateLimiter
     */
    @RateLimiter(name = "orderRateLimiter", fallbackMethod = "fallbackDeleteOrder")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // ✅ Fallback methods (executed if RateLimiter is triggered)

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public List<Order> fallbackGetAllOrders(Throwable t) {
        System.out.println("⚠️ fallbackGetAllOrders: Rate limiter triggered - " + t.getMessage());
        return Collections.emptyList();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Optional<Order> fallbackGetOrderById(Long id, Throwable t) {
        System.out.println("⚠️ fallbackGetOrderById: Rate limiter triggered for ID " + id + " - " + t.getMessage());
        return Optional.empty();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Order fallbackSaveOrder(Order order, Throwable t) {
        System.out.println("⚠️ fallbackSaveOrder: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Order fallbackUpdateOrderStatus(Long id, String newStatus, Throwable t) {
        System.out.println("⚠️ fallbackUpdateOrderStatus: Rate limiter triggered - " + t.getMessage());
        return null;
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void fallbackDeleteOrder(Long id, Throwable t) {
        System.out.println("⚠️ fallbackDeleteOrder: Rate limiter triggered - " + t.getMessage());
    }
}