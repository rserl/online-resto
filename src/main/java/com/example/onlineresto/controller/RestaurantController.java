package com.example.onlineresto.controller;

import com.example.onlineresto.entity.Restaurant;
import com.example.onlineresto.service.RestaurantService;
import com.example.onlineresto.utils.constant.ApiUrlConstant;
import com.example.onlineresto.utils.constant.RestaurantMessageConstant;
import com.example.onlineresto.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.RESTAURANT_PATH)
@AllArgsConstructor
public class RestaurantController {
    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Response<Restaurant>> saveRestaurant(@RequestBody Restaurant restaurant){
        Response<Restaurant> response = new Response<>();
        response.setMessage(RestaurantMessageConstant.INSERT_DATA_SUCCESS);
        response.setData(restaurantService.save(restaurant));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurant(){
        return restaurantService.findAll();
    }

    @PutMapping
    public Restaurant updateRestaurant(@RequestBody Restaurant restaurant){
        return restaurantService.update(restaurant);
    }

    @GetMapping("/{id}")
    public Restaurant getFoodById(@PathVariable String id){
        return restaurantService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable String id){
        restaurantService.delete(id);
    }
}
