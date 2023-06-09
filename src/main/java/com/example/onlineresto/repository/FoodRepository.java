package com.example.onlineresto.repository;

import com.example.onlineresto.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {
    @Query(value = "SELECT * FROM mst_food f WHERE f.stock < 3", nativeQuery = true)
    List<Food> findFoodMinStock();
}
