package com.example.onlineresto.repository;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    @Query(value = "SELECT f.food_name " +
            "FROM mst_food f " +
            "JOIN trx_order_detail o ON f.food_id = o.food_id " +
            "GROUP BY f.food_id " +
            "ORDER BY SUM(o.quantity) DESC " +
            "LIMIT 3", nativeQuery = true)
    List<String> findBestSeller();
}
