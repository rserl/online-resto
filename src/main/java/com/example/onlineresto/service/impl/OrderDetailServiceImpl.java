package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.entity.Order;
import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.repository.OrderDetailRepository;
import com.example.onlineresto.repository.OrderRepository;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.utils.customResponse.OrderWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<String> bestSeller() {
        List<String> bestSellers = orderDetailRepository.findBestSeller();
        for (String foodName : bestSellers) {
            System.out.println(foodName);
        }
        return bestSellers;
    }
}
