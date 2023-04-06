package com.example.onlineresto.repository;

import com.example.onlineresto.dto.OrderStatus;
import com.example.onlineresto.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByOrderStatus(OrderStatus status);
}
