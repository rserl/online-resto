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
    public OrderWrapper orderTransaction(String orderId) {
        Order transaction = orderRepository.findById(orderId).get();
        String customerName = transaction.getCustomer().getCustomerName();
        Date purchaseDate = transaction.getOrderDate();
        List<HashMap<String, String>> newListOrder = new ArrayList<>();

        Integer subTotal = null;
        Integer total = null;

        for (OrderDetail orderDetail: transaction.getOrderDetails()){
            HashMap<String, String> foodOrder = new HashMap<String, String>();
            String foodName = orderDetail.getFood().getFoodName();
            Integer orderPrice = orderDetail.getItemPrice();
            Integer quantity = orderDetail.getQuantity();
            subTotal = orderPrice * quantity;
            total += subTotal;

            foodOrder.put("foodName", foodName);
            foodOrder.put("itemPrice", orderPrice.toString());
            foodOrder.put("quantity", quantity.toString());
            foodOrder.put("subTotal", subTotal.toString());
            foodOrder.put("total", total.toString());
            newListOrder.add(foodOrder);
        }

        return null;
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
