package com.example.onlineresto.repository;

import com.example.onlineresto.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, String> {
}
