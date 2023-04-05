package com.example.onlineresto.service;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.utils.customResponse.OrderWrapper;

import java.util.List;

public interface OrderDetailService {
    OrderDetail save(OrderDetail orderDetail);
    OrderWrapper orderTransaction(String orderId);
    List<String> bestSeller();
}
