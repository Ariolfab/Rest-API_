package com.Resilencepattern.sneps_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Resilencepattern.sneps_api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
