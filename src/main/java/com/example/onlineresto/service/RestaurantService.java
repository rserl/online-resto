package com.example.onlineresto.service;

import com.example.onlineresto.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant save(Restaurant restaurant);
    List<Restaurant> findAll();
    Restaurant update(Restaurant restaurant);
    Restaurant getById(String id);
    void delete(String id);
}
