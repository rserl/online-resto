package com.example.onlineresto.service.impl;

import com.example.onlineresto.dto.OrderDTO;
import com.example.onlineresto.dto.OrderDetailDTO;
import com.example.onlineresto.entity.Customer;
import com.example.onlineresto.entity.Food;
import com.example.onlineresto.entity.Order;
import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.repository.FoodRepository;
import com.example.onlineresto.repository.OrderRepository;
import com.example.onlineresto.service.CustomerService;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.service.OrderService;
import com.example.onlineresto.utils.constant.FoodMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderDetailService orderDetailService;
    FoodRepository foodRepository;
    CustomerService customerService;

    @Transactional
    @Override
    public Order save(Order order) {
        Order saveOrder = orderRepository.save(order);

        Customer customer = customerService.getById(saveOrder.getCustomer().getId());
        saveOrder.setCustomer(customer);

        saveOrder.setOrderDate(Date.valueOf(LocalDate.now()));

        Integer amount = 0;

        for (OrderDetail orderDetail: order.getOrderDetails()){
            Food food = foodRepository.findById(orderDetail.getFood().getId()).get();
            food.setStock(food.getStock() - orderDetail.getQuantity());
            if (food.getStock() < 1){
                throw new DataNotFoundException(FoodMessageConstant.OUT_OF_STOCK_FOOD);
            }

            food.setPrice(food.getPrice());
            foodRepository.save(food);

            orderDetail.setOrder(saveOrder);
            orderDetailService.save(orderDetail);

            orderDetail.setFood(food);

        }
        return saveOrder;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDTO getById(String id) {
        if (orderRepository.findById(id).isPresent()){
            Order order = orderRepository.findById(id).get();
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setOrderDate(orderDTO.getOrderDate());
            orderDTO.setCustomerName(order.getCustomer().getCustomerName());
            List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

            Integer amount = 0;

            for (OrderDetail orderDetail: order.getOrderDetails()){
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setFoodName(orderDetail.getFood().getFoodName());
                orderDetailDTO.setQuantity(orderDetail.getQuantity());
                orderDetailDTO.setPriceSell(orderDetail.getItemPrice());

                Integer subTotal = orderDetail.getItemPrice() * orderDetail.getQuantity();

                orderDetailDTO.setSubTotal(subTotal);
                amount += subTotal;

                orderDetailDTOS.add(orderDetailDTO);
            }

            orderDTO.setTotal(amount);
            orderDTO.setOrderDetailDTOS(orderDetailDTOS);

            return orderDTO;
        } else {
            throw new NoSuchElementException();
        }
    }
}
