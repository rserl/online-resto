package com.example.onlineresto.controller;

import com.example.onlineresto.entity.Order;
import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.service.OrderService;
import com.example.onlineresto.utils.constant.ApiUrlConstant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrlConstant.ORDER_DETAIL_PATH)
@AllArgsConstructor
public class OrderDetailController {
    OrderDetailService orderDetailService;
    OrderService orderService;

    @PostMapping("/details")
    public OrderDetail orderDetail(@RequestBody OrderDetail orderDetail){
        return orderDetailService.save(orderDetail);
    }

    @PostMapping
    public Order saveOrder(@RequestBody Order order){
        return orderService.save(order);
    }
}
