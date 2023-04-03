package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Customer;
import com.example.onlineresto.repository.CustomerRepository;
import com.example.onlineresto.service.CustomerService;
import com.example.onlineresto.utils.constant.CustomerMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer) {
        if (customerRepository.findById(customer.getId()).isPresent()){
            return customerRepository.save(customer);
        } else {
            throw new DataNotFoundException(String.format(CustomerMessageConstant.CUSTOMER_UPDATE_NOT_FOUND, customer.getId()));
        }
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
