package com.example.onlineresto.repository;

import com.example.onlineresto.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
