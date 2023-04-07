package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.OrderDetail;
import com.example.onlineresto.repository.OrderDetailRepository;
import com.example.onlineresto.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceImplTest {
    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Test
    void save_order_detail() {
        OrderDetail orderDetail = new OrderDetail();

        //mock
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);

        //perform
        OrderDetail savedOrderDetail = orderDetailService.save(orderDetail);

        //verify result
        assertNotNull(savedOrderDetail);
        assertEquals(orderDetail, savedOrderDetail);

        //verify behaviour
        verify(orderDetailRepository, times(1)).save(orderDetail);
    }

    @Test
    void bestSeller_test() {
        List<String> bestSellers = new ArrayList<>();
        bestSellers.add("food 1");
        bestSellers.add("food 2");

        //mock
        when(orderDetailRepository.findBestSeller()).thenReturn(bestSellers);

        //perform
        List<String> result = orderDetailService.bestSeller();

        //verify result
        assertNotNull(result);
        assertEquals(2, bestSellers.size());
        assertEquals(bestSellers.get(0), result.get(0));
        assertTrue(result.contains(result.get(1)));

        //verify behaviour
        verify(orderDetailRepository, times(1)).findBestSeller();
    }

    @Test
    void bestSeller_test_null_value() {
        //mock
        when(orderDetailRepository.findBestSeller()).thenReturn(null);

        //perform
        List<String> result = orderDetailService.bestSeller();

        //verify result
        assertNotNull(result);
        assertEquals(0, result.size());

        //verify behaviour
        verify(orderDetailRepository, times(1)).findBestSeller();
    }
}