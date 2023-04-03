package com.example.onlineresto.utils.customResponse;

import java.sql.Date;
import java.util.List;

public class OrderWrapper<T> {
    private String orderId;
    private String customerName;
    private Date purchaseDate;
    private List<T> orderDetail;
    private Integer subTotal;
    private Integer total;

    public OrderWrapper(String orderId, String customerName, Date purchaseDate, List<T> orderTransaction, Integer subTotal, Integer total) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.purchaseDate = purchaseDate;
        this.orderDetail = orderTransaction;
        this.subTotal = subTotal;
        this.total = total;
    }
}
