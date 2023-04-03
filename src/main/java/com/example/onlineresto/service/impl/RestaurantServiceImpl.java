package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Restaurant;
import com.example.onlineresto.repository.RestaurantRepository;
import com.example.onlineresto.service.RestaurantService;
import com.example.onlineresto.utils.constant.RestaurantMessageConstant;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    RestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        if (restaurantRepository.findById(restaurant.getId()).isPresent()){
            return restaurantRepository.save(restaurant);
        } else {
            throw new DataNotFoundException(String.format(RestaurantMessageConstant.RESTAURANT_NOT_FOUND, restaurant.getId()));
        }
    }

    @Override
    public Restaurant getById(String id) {
        if (restaurantRepository.findById(id).isPresent()){
            return restaurantRepository.findById(id).get();
        } else {
            throw new DataNotFoundException(String.format(RestaurantMessageConstant.RESTAURANT_NOT_FOUND, id));
        }
    }

    @Override
    public void delete(String id) {
        restaurantRepository.deleteById(id);
    }
}
