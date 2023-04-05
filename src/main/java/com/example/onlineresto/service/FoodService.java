package com.example.onlineresto.service;

import com.example.onlineresto.entity.Food;

import java.util.List;

public interface FoodService {
    Food save(Food food);
    List<Food> findAll();
    Food update(Food food);
    Food getById(String id);
    void delete(String id);
//    List<Food> bestSeller();
}
