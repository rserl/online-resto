package com.example.onlineresto.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private String restaurantName;
    private String orderId;
    private Date orderDate;
    private String customerName;
    private String orderStatus;
    private Integer total;
    private List<OrderDetailDTO> orderDetailDTOS;
}
