package com.example.onlineresto.controller;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.service.FoodService;
import com.example.onlineresto.utils.constant.ApiUrlConstant;
import com.example.onlineresto.utils.constant.FoodMessageConstant;
import com.example.onlineresto.utils.customResponse.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.FOOD_PATH)
@AllArgsConstructor
public class FoodController {
    FoodService foodService;

    @PostMapping
    public ResponseEntity<Response<Food>> saveFood(@RequestBody Food food){
        Response<Food> response = new Response<>();
        response.setMessage(FoodMessageConstant.INSERT_DATA_SUCCESS);
        response.setData(foodService.save(food));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public List<Food> getAllFood(){
        return foodService.findAll();
    }

    @PutMapping
    public Food updateFood(@RequestBody Food food){
        return foodService.update(food);
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable String id){
        return foodService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable String id){
        foodService.delete(id);
    }
}
