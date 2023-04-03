package com.example.onlineresto.controller;

import com.example.onlineresto.dto.OrderDTO;
import com.example.onlineresto.entity.Order;
import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.service.OrderService;
import com.example.onlineresto.utils.constant.ApiUrlConstant;
import com.example.onlineresto.utils.constant.OrderMessageConstant;
import com.example.onlineresto.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.ORDER_PATH)
@AllArgsConstructor
public class OrderController {
    OrderService orderService;
    OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<Response<Order>> saveOrder(@RequestBody Order order){
        Response<Order> response = new Response<>();
        response.setMessage(OrderMessageConstant.INSERT_DATA_SUCCESS);
        response.setData(orderService.save(order));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable String id){
        return orderService.getById(id);
    }

    @PostMapping("/details")
    public OrderDetail orderDetail(@RequestBody OrderDetail orderDetail){
        return orderDetailService.save(orderDetail);
    }
}
