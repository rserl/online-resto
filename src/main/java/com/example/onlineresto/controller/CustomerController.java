package com.example.onlineresto.controller;

import com.example.onlineresto.entity.Customer;
import com.example.onlineresto.service.CustomerService;
import com.example.onlineresto.utils.constant.ApiUrlConstant;
import com.example.onlineresto.utils.constant.CustomerMessageConstant;
import com.example.onlineresto.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER_PATH)
@AllArgsConstructor
public class CustomerController {
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response<Customer>> saveCustomer(@RequestBody Customer customer){
        Response<Customer> response = new Response<>();
        response.setMessage(CustomerMessageConstant.INSERT_DATA_SUCCESS);
        response.setData(customerService.save(customer));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.findAll();
    }

    @PutMapping
    Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable String id){
        customerService.delete(id);
    }
}
