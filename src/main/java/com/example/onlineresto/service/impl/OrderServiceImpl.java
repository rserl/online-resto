package com.example.onlineresto.service.impl;

import com.example.onlineresto.dto.*;
import com.example.onlineresto.entity.*;
import com.example.onlineresto.repository.FoodRepository;
import com.example.onlineresto.repository.OrderRepository;
import com.example.onlineresto.service.CustomerService;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.service.OrderService;
import com.example.onlineresto.service.RestaurantService;
import com.example.onlineresto.utils.constant.FoodMessageConstant;
import com.example.onlineresto.utils.constant.OrderMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderDetailService orderDetailService;
    FoodRepository foodRepository;
    CustomerService customerService;
    RestaurantService restaurantService;

    @Transactional
    @Override
    public Order save(Order order) {
        Order saveOrder = orderRepository.save(order);

        Customer customer = customerService.getById(saveOrder.getCustomer().getId());
        saveOrder.setCustomer(customer);

        Restaurant restaurant = restaurantService.getById(saveOrder.getRestaurant().getId());
        saveOrder.setRestaurant(restaurant);

        saveOrder.setOrderDate(Date.valueOf(LocalDate.now()));

        Integer amount = 0;
        for (OrderDetail orderDetail: order.getOrderDetails()){
            Food food = foodRepository.findById(orderDetail.getFood().getId()).get();
            food.setStock(food.getStock() - orderDetail.getQuantity());
            if (food.getStock() < 1){
                throw new DataNotFoundException(FoodMessageConstant.OUT_OF_STOCK_FOOD);
            }

            foodRepository.save(food);

            orderDetail.setFood(food);
            orderDetail.setItemPrice(food.getPrice());
            orderDetail.setOrder(saveOrder);
            orderDetailService.save(orderDetail);

            Integer subTotal = orderDetail.getItemPrice() * orderDetail.getQuantity();
            amount += subTotal;
        }
        saveOrder.setTotalPrice(amount);
        saveOrder.setOrderStatus(OrderStatus.PENDING.name());
        return saveOrder;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order update(String id, OrderStatusDTO status) {
        Optional<Order> getOrder = orderRepository.findById(id);
        if (getOrder.isPresent()){
            Order orders = getOrder.get();

            switch (status.getStatus()){
                case 1:
                    orders.setOrderStatus(OrderStatus.ON_PROCESS.name());
                    break;
                case 2:
                    orders.setOrderStatus(OrderStatus.REJECTED.name());
                    break;
                case 3:
                    orders.setOrderStatus(OrderStatus.COMPLETED.name());
                    break;
                default:
                    orders.setOrderStatus(OrderStatus.PENDING.name());
            }
            if (orders.getOrderStatus() == OrderStatus.REJECTED.name()) {
                orderRepository.deleteById(id);
            }

            return orderRepository.save(orders);
        } else throw new DataNotFoundException(String.format(OrderMessageConstant.ORDER_NOT_FOUND, id));
    }

    @Override
    public OrderDTO getById(String id) {
        if (orderRepository.findById(id).isPresent()){
            Order order = orderRepository.findById(id).get();
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setOrderDate(order.getOrderDate());
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
        } else throw new NoSuchElementException();
    }

    @Override
    public TotalIncomeDTO totalIncome() {
        List<Order> allOrders = orderRepository.findAll();

        if (allOrders == null || allOrders.isEmpty()) {
            return null;
        }

        Integer totalIncome = allOrders.stream()
                .map(Order::getTotalPrice)
                .filter(order -> order != null)
                .reduce(0, Integer::sum);

        TotalIncomeDTO totalIncomeDTO = new TotalIncomeDTO();
        totalIncomeDTO.setTotalOrder(allOrders.size());
        totalIncomeDTO.setTotalIncome(totalIncome);

        return totalIncomeDTO;
    }
}
