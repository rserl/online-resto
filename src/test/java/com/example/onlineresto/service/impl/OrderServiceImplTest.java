package com.example.onlineresto.service.impl;

import com.example.onlineresto.dto.*;
import com.example.onlineresto.entity.*;
import com.example.onlineresto.repository.FoodRepository;
import com.example.onlineresto.repository.OrderRepository;
import com.example.onlineresto.service.CustomerService;
import com.example.onlineresto.service.OrderDetailService;
import com.example.onlineresto.service.RestaurantService;
import com.example.onlineresto.utils.constant.FoodMessageConstant;
import com.example.onlineresto.utils.constant.OrderMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import com.example.onlineresto.utils.exception.InvalidRequestException;
import com.example.onlineresto.utils.exception.OutOfStockException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private Order order;
    private String id;
    private Customer customer;
    private Restaurant restaurant;
    private Food food;
    private OrderDetail orderDetail;

    @BeforeEach
    void setup(){
        id = "ord-01";
        customer = new Customer("01", "customer name", "customer@gmail.com", "081293789099", "jakarta");
        restaurant = new Restaurant("r01", "hoki hoki bento", "hokihoki@bento.com", "087882930001", "Kembangan");
        food = new Food("f001", "cheese cake", 25000, 2);
        orderDetail = new OrderDetail("ord-001", order, food, 25000, 1);
        order = new Order(id, customer, restaurant, Date.valueOf("2023-04-07"), 52000, OrderStatus.PENDING, Collections.singletonList(orderDetail));
    }

    @AfterEach
    void cleanup(){
        customer = new Customer();
        restaurant = new Restaurant();
        food = new Food();
        orderDetail = new OrderDetail();
        order = new Order();
    }
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderDetailService orderDetailService;
    @Mock
    FoodRepository foodRepository;
    @Mock
    CustomerService customerService;
    @Mock
    RestaurantService restaurantService;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void save_new_order_success() {
        //mock
        when(orderRepository.save(order)).thenReturn(order);
        when(customerService.getById(any())).thenReturn(order.getCustomer());
        when(restaurantService.getById(any())).thenReturn(order.getRestaurant());
        when(foodRepository.findById(any())).thenReturn(Optional.of(order.getOrderDetails().get(0).getFood()));
        when(orderDetailService.save(any(OrderDetail.class))).thenReturn(new OrderDetail());

        //perform
        Order savedOrder = orderService.save(order);

        //verify result
        assertNotNull(savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(order.getOrderDate(), savedOrder.getOrderDate());
        assertEquals(OrderStatus.PENDING, savedOrder.getOrderStatus());

        //verify behaviour
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void save_new_order_failed_out_of_stock_food() {
//        food.setStock(3);
//        OrderDetail orderDetail2 = new OrderDetail("ord-001", order, food, 25000, 5);
//        Order order2 = new Order(id, customer, restaurant, Date.valueOf("2023-04-07"), 52000, OrderStatus.PENDING, Collections.singletonList(orderDetail2));
//////
//////        //mock
////        when(customerService.getById(any())).thenReturn(customer);
////        when(restaurantService.getById(any())).thenReturn(restaurant);
//        when(foodRepository.findById(any())).thenReturn(Optional.of(food));
////
////        //verify behaviour
//        assertThrows(OutOfStockException.class, () -> orderService.save(order2));
//        verify(orderRepository, never()).save(order2);
    }

    @Test
    void findAll() {
        List<Order> orders = Collections.singletonList(order);

        //mock
        when(orderRepository.findAll()).thenReturn(orders);

        //perform
        List<OrderDTO> orderDTOS = orderService.findAll();

        //verify result
        assertNotNull(orderDTOS);
        assertEquals(orders.size(), orderDTOS.size());
    }

    @Test
    void update_existing_order_pending() {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(4);

        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //perform
        OrderStatusDTO updatedStatus = orderService.update(id, updateStatusDTO);

        //verify result
        assertNotNull(updatedStatus);
        assertEquals(OrderStatus.PENDING.toString(), updatedStatus.getStatusOrder());
        assertEquals(order.getCustomer().getCustomerName(), updatedStatus.getCustomerName());

        //verify behaviour
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void update_existing_order_in_process() {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(1);

        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //perform
        OrderStatusDTO updatedStatus = orderService.update(id, updateStatusDTO);

        //verify result
        assertNotNull(updatedStatus);
        assertEquals(OrderStatus.IN_PROCESS.toString(), updatedStatus.getStatusOrder());
        assertEquals(order.getCustomer().getCustomerName(), updatedStatus.getCustomerName());

        //verify behaviour
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void update_existing_order_rejected() {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(2);

        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        //verify behaviour
        assertThrows(InvalidRequestException.class, () -> orderService.update(id, updateStatusDTO));
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void update_existing_order_completed() {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(3);

        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //perform
        OrderStatusDTO updatedStatus = orderService.update(id, updateStatusDTO);

        //verify result
        assertNotNull(updatedStatus);
        assertEquals(OrderStatus.COMPLETED.toString(), updatedStatus.getStatusOrder());
        assertEquals(order.getCustomer().getCustomerName(), updatedStatus.getCustomerName());

        //verify behaviour
        verify(orderRepository, times(1)).findById(id);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void update_existing_order_already_completed() {
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(3);

        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order(id, customer, restaurant, Date.valueOf("2023-04-07"), 52000, OrderStatus.COMPLETED, Collections.singletonList(orderDetail))));

        //verify behaviour
        assertThrows(InvalidRequestException.class, () -> orderService.update(id, updateStatusDTO));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void update_non_existing_order() {
        String wrongId = "ord-10";
        UpdateStatusDTO updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setStatus(1);

        //mock
       when(orderRepository.findById(wrongId)).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> orderService.update(wrongId, updateStatusDTO),
                String.format(OrderMessageConstant.ORDER_NOT_FOUND, wrongId));
        verify(orderRepository, times(1)).findById(wrongId);
        verify(orderRepository, never()).save(any());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void getById_existing_data() {
        order.setOrderStatus(OrderStatus.COMPLETED);
        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        //perform
        OrderDTO orderDTO = orderService.getById(id);

        //verify result
        assertNotNull(orderDTO);
        assertEquals(orderDTO.getOrderId(), id);
        assertEquals(orderDTO.getOrderDate(), order.getOrderDate());

        //verify behaviour
        verify(orderRepository, times(2)).findById(id);
    }

    @Test
    void getById_not_existing_data() {
        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> orderService.getById(id), String.format(OrderMessageConstant.ORDER_NOT_FOUND, id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void getById_invalid_status() {
        //mock
        when(orderRepository.findById(id)).thenReturn(Optional.of(new Order(id, customer, restaurant, Date.valueOf("2023-04-07"), 52000, OrderStatus.PENDING, Collections.singletonList(orderDetail))));

        //verify behaviour
        assertThrows(InvalidRequestException.class, () -> orderService.getById(id), OrderMessageConstant.ORDER_INCOMPLETE);
        verify(orderRepository, times(2)).findById(id);
    }

    @Test
    void totalIncome_existing_data() {
        List<Order> completedOrders = new ArrayList<>();
        completedOrders.add(order);

        //mock
        when(orderRepository.findByOrderStatus(OrderStatus.COMPLETED)).thenReturn(completedOrders);

        //perform
        TotalIncomeDTO totalIncomeDTO = orderService.totalIncome();

        //verify result
        assertEquals(1, totalIncomeDTO.getTotalOrder());
        assertEquals(52000, totalIncomeDTO.getTotalIncome());

        //verify behaviour
        verify(orderRepository, times(1)).findByOrderStatus(OrderStatus.COMPLETED);
    }

    @Test
    void totalIncome_not_existing_data() {
        List<Order> completedOrders = new ArrayList<>();

        //mock
        when(orderRepository.findByOrderStatus(OrderStatus.COMPLETED)).thenReturn(completedOrders);

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> orderService.totalIncome(), String.format(OrderMessageConstant.NO_RECORD_DATA));
    }
}