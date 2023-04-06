package com.example.onlineresto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO {
    private String orderId;
    private String customerName;
    private String statusOrder;
}
