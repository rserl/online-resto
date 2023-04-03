package com.example.onlineresto.repository;

import com.example.onlineresto.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
}
