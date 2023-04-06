package com.example.onlineresto.service.impl;

import com.example.onlineresto.entity.Restaurant;
import com.example.onlineresto.repository.RestaurantRepository;
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
class RestaurantServiceImplTest {
    Restaurant restaurant;
    String id;

    @BeforeEach
    void setup(){
        id = "01";
        restaurant = new Restaurant("r01", "hoki hoki bento", "hokihoki@bento.com", "087882930001", "Kembangan");
    }

    @AfterEach
    void cleanup(){
        restaurant = new Restaurant();
    }

    @Mock
    RestaurantRepository restaurantRepository;
    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @Test
    void save_restaurant_data() {
        //mock
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        //perform
        Restaurant savedRestaurant = restaurantService.save(restaurant);

        //verify result
        assertNotNull(savedRestaurant);
        assertEquals(restaurant.getId(), savedRestaurant.getId());
        assertEquals(restaurant.getRestaurantName(), savedRestaurant.getRestaurantName());


        //verify behaviour
        verify(restaurantRepository, times(1)).save(restaurant);
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void find_all_restaurants() {
        List<Restaurant> listOfRestaurant = new ArrayList<>();
        Restaurant restaurant2= new Restaurant("r02", "makdi", "makdi@delivery.com", "087883098990", "Kebon Jeruk");
        listOfRestaurant.add(restaurant);
        listOfRestaurant.add(restaurant2);

        //mock
        when(restaurantRepository.findAll()).thenReturn(listOfRestaurant);

        //perform
        List<Restaurant> getAllRestaurant = restaurantService.findAll();

        //verify result
        assertNotNull(getAllRestaurant);
        assertEquals(listOfRestaurant.size(), getAllRestaurant.size());
        assertEquals(listOfRestaurant.get(0).getPhone(), getAllRestaurant.get(0).getPhone());
        assertEquals(listOfRestaurant.get(1).getEmail(), getAllRestaurant.get(1).getEmail());

        //verify behaviour
        verify(restaurantRepository, times(1)).findAll();
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void update_existing_restaurant() {
        //mock
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        //perform
        Restaurant updatedRestaurant = restaurantService.update(restaurant);

        //verify result
        assertNotNull(updatedRestaurant);
        assertEquals(restaurant.getAddress(), updatedRestaurant.getAddress());
        assertEquals(restaurant.getRestaurantName(), updatedRestaurant.getRestaurantName());

        //verify behaviour
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
        verify(restaurantRepository, times(1)).save(restaurant);
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void update_not_existing_restaurant() {
        //mock
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> restaurantService.update(restaurant));
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
        verify(restaurantRepository, never()).save(restaurant);
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getById_existing_restaurant() {
        //mock
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        //perform
        Restaurant existingRestaurant = restaurantService.getById(id);

        //verify result
        assertNotNull(existingRestaurant);
        assertEquals(restaurant.getId(), existingRestaurant.getId());
        assertEquals(restaurant.getEmail(), existingRestaurant.getEmail());

        //verify behaviour
        verify(restaurantRepository, times(2)).findById(id);
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void getById_not_existing_restaurant() {
        //mock
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        //verify behaviour
        assertThrows(DataNotFoundException.class, () -> restaurantService.getById(id));
        verify(restaurantRepository, times(1)).findById(id);
        verifyNoMoreInteractions(restaurantRepository);
    }

    @Test
    void delete_existing_customer() {
        //mock
        doNothing().when(restaurantRepository).deleteById(id);

        //perform
        restaurantService.delete(id);

        //verify behaviour
        verify(restaurantRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(restaurantRepository);
    }
}