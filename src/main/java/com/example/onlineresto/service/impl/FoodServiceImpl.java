package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.repository.FoodRepository;
import com.example.onlineresto.service.FoodService;
import com.example.onlineresto.utils.constant.FoodMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {
    FoodRepository foodRepository;

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food update(Food food) {
        if (foodRepository.findById(food.getId()).isPresent()){
            return foodRepository.save(food);
        } else {
            throw new DataNotFoundException(String.format(FoodMessageConstant.FOOD_NOT_FOUND, food.getId()));
        }
    }

    @Override
    public Food getById(String id) {
        return foodRepository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        foodRepository.deleteById(id);
    }
}
