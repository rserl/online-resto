package com.example.onlineresto.service;

import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.utils.customResponse.OrderWrapper;

public interface OrderDetailService {
    OrderDetail save(OrderDetail orderDetail);
    OrderWrapper orderTransaction(String orderId);
}
