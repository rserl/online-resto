package com.example.onlineresto.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private String orderId;
    private String customerName;
    private Integer total;
    private Integer orderDate;
    private List<OrderDetailDTO> orderDetailDTOS;
}
