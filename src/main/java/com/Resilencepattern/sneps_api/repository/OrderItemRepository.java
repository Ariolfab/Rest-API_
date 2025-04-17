package com.Resilencepattern.sneps_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Resilencepattern.sneps_api.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
