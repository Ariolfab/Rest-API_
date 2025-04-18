package com.Resilencepattern.sneps_api.service;

import com.Resilencepattern.sneps_api.model.OrderItem;
import com.Resilencepattern.sneps_api.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Récupérer tous les OrderItems
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Récupérer un OrderItem par ID
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Enregistrer un nouveau OrderItem
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Mettre à jour un OrderItem existant
    public OrderItem updateOrderItem(Long id, OrderItem updatedItem) {
        return orderItemRepository.findById(id).map(orderItem -> {
            orderItem.setQuantity(updatedItem.getQuantity());
            orderItem.setPrice(updatedItem.getPrice());
            orderItem.setProduct(updatedItem.getProduct());
            orderItem.setOrder(updatedItem.getOrder());
            return orderItemRepository.save(orderItem);
        }).orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    // Supprimer un OrderItem
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}

