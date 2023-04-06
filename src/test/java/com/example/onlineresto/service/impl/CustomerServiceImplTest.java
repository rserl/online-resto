package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Customer;
import com.example.onlineresto.repository.CustomerRepository;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    private Customer customer;
    private String id;

    @BeforeEach
    void setup(){
        id = "01";
        customer = new Customer("01", "customer name", "customer@gmail.com", "081293789099", "jakarta");
    }

    @AfterEach
    void cleanup(){
        customer = new Customer();
    }

    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    void save_new_customer() {
        //mock
        when(customerRepository.save(customer)).thenReturn(customer);

        //perform
        Customer savedCustomer = customerService.save(customer);

        //verify result
        assertNotNull(savedCustomer);
        assertEquals(customer.getCustomerName(), savedCustomer.getCustomerName());
        assertEquals(customer.getAddress(), savedCustomer.getAddress());

        //verify behaviour
        verify(customerRepository, times(1)).save(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void find_all_customers() {
        List<Customer> listOfCustomers = new ArrayList<>();
        Customer customer2 = new Customer("02", "new customer", "customer2@gmail.com", "081227898990", "bali");
        listOfCustomers.add(customer);
        listOfCustomers.add(customer2);

        //mock
        when(customerRepository.findAll()).thenReturn(listOfCustomers);

        //perform
        List<Customer> getAllCustomers = customerService.findAll();

        //verify result
        assertNotNull(getAllCustomers);
        assertEquals(listOfCustomers.size(), getAllCustomers.size());
        assertEquals("01", getAllCustomers.get(0).getId());
        assertEquals("02", getAllCustomers.get(1).getId());

        //verify behaviour
        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void update_existing_customer() {
        //mock
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        //perform
        Customer updatedCustomer = customerService.update(customer);

        //verify result
        assertNotNull(updatedCustomer);
        assertEquals(customer.getId(), updatedCustomer.getId());
        assertEquals(customer.getPhone(), updatedCustomer.getPhone());

        //verify behaviour
        verify(customerRepository, times(1)).findById(customer.getId());
        verify(customerRepository, times(1)).save(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void update_non_existing_customer() {
        //mock
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> customerService.update(customer));
        verify(customerRepository, times(1)).findById(customer.getId());
        verify(customerRepository, never()).save(customer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getById_existing_customer() {
        //mock
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        //perform
        Customer existingCustomer = customerService.getById(id);

        //verify result
        assertNotNull(existingCustomer);
        assertEquals(customer.getAddress(),  existingCustomer.getAddress());
        assertEquals(customer.getEmail(), existingCustomer.getEmail());

        //verify behaviour
        verify(customerRepository, times(2)).findById(id);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getById_non_existing_customer() {
        //mock
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> customerService.getById(id));
        verify(customerRepository, times(1)).findById(id);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void delete_existing_customer() {
        //mock
        doNothing().when(customerRepository).deleteById(id);

        //perform
        customerService.delete(id);

        //verify behaviour
        verify(customerRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(customerRepository);
    }
}