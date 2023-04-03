package com.example.onlineresto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
    private String foodName;
    private Integer quantity;
    private Integer priceSell;
    private Integer subTotal;
}
