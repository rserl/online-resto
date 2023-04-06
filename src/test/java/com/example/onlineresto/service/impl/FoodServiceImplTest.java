package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Food;
import com.example.onlineresto.repository.FoodRepository;
import com.example.onlineresto.utils.exception.DataNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {
    private Food food;
    private String id;

    @BeforeEach
    void setup(){
        id = "01";
        food = new Food("f001", "cheese cake", 25000, 20);
    }

    @AfterEach
    void cleanup(){
        food = new Food();
    }

    @Mock
    FoodRepository foodRepository;

    @InjectMocks
    FoodServiceImpl foodService;

    @Test
    void save_new_food() {
        //mock
        when(foodRepository.save(food)).thenReturn(food);

        //perform
        Food savedFood = foodService.save(food);

        //verify result
        assertNotNull(savedFood);
        assertEquals(food.getId(), savedFood.getId());
        assertEquals(food.getFoodName(), savedFood.getFoodName());

        //verify behaviour
        verify(foodRepository, times(1)).save(food);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void find_all_foods() {
        List<Food> listOfFoods = new ArrayList<>();
        Food food2 = new Food("f002", "black forest", 22000, 15);
        listOfFoods.add(food);
        listOfFoods.add(food2);

        //mock
        when(foodRepository.findAll()).thenReturn(listOfFoods);

        //perform
        List<Food> getAllFoods = foodService.findAll();

        //verify result
        assertNotNull(getAllFoods);
        assertEquals(listOfFoods.size(), getAllFoods.size());
        assertEquals(listOfFoods.get(0).getStock(), getAllFoods.get(0).getStock());
        assertEquals(listOfFoods.get(1).getPrice(), listOfFoods.get(1).getPrice());

        //verify behaviour
        verify(foodRepository, times(1)).findAll();
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void update_existing_food() {
        //mock
        when(foodRepository.findById(food.getId())).thenReturn(Optional.of(food));
        when(foodRepository.save(food)).thenReturn(food);

        //perform
        Food updatedFood = foodService.update(food);

        //verify result
        assertNotNull(updatedFood);
        assertEquals(food.getId(), updatedFood.getId());
        assertEquals(food.getStock(), food.getStock());

        //verify behaviour
        verify(foodRepository, times(1)).findById(food.getId());
        verify(foodRepository, times(1)).save(food);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void update_not_existing_food() {
        //mock
        when(foodRepository.findById(food.getId())).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> foodService.update(food));
        verify(foodRepository, times(1)).findById(food.getId());
        verify(foodRepository, never()).save(food);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void getById_existing_food() {
        //mock
        when(foodRepository.findById(id)).thenReturn(Optional.of(food));

        //perform
        Food existingFood = foodService.getById(id);

        //verify result
        assertNotNull(existingFood);
        assertEquals(food.getFoodName(), existingFood.getFoodName());
        assertEquals(food.getStock(), existingFood.getStock());

        //verify behaviour
        verify(foodRepository, times(2)).findById(id);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void getById_not_existing_food() {
        //mock
        when(foodRepository.findById(id)).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> foodService.getById(id));
        verify(foodRepository, times(1)).findById(id);
        verifyNoMoreInteractions(foodRepository);
    }

    @Test
    void delete_existing_food() {
        //mock
        doNothing().when(foodRepository).deleteById(id);

        //perform
        foodService.delete(id);

        //verify behaviour
        verify(foodRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(foodRepository);
    }
}