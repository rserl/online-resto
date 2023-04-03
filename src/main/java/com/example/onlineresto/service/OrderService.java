package com.example.onlineresto.service;

import com.example.onlineresto.dto.OrderDTO;
import com.example.onlineresto.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> findAll();
    OrderDTO getById(String id);
}
