package com.example.onlineresto.service;

import com.example.onlineresto.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Customer update(Customer customer);
    Customer getById(String id);
    void delete (String id);
}
