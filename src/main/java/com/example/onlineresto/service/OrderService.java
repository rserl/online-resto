package com.example.onlineresto.service;

import com.example.onlineresto.dto.OrderDTO;
import com.example.onlineresto.dto.OrderStatusDTO;
import com.example.onlineresto.dto.TotalIncomeDTO;
import com.example.onlineresto.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    List<Order> findAll();
    Order update(String id, OrderStatusDTO status);
    OrderDTO getById(String id);
    TotalIncomeDTO totalIncome();
}
