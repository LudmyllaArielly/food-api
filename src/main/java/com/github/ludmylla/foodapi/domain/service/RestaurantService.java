package com.github.ludmylla.foodapi.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ludmylla.foodapi.domain.exceptions.EntityNotFoundException;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenService kitchenService;

    public Restaurant create(Restaurant restaurant){
        verifyIfKitchenExist(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll(){
        List<Restaurant> list = restaurantRepository.findAll();
        return list.stream().distinct().sorted(Comparator.comparing(Restaurant::getId))
                .collect(Collectors.toList());
    }

    public Restaurant findById(Long id){
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant does not exist."));
    }

    public Restaurant update(Long id, Restaurant restaurant){
        verifyIfKitchenExist(restaurant);
        Restaurant restaurantActual = findById(id);

        BeanUtils.copyProperties(restaurant, restaurantActual,
                "id","formOfPayment", "address", "registrationDate", "products");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant partialUpdate(Long id, Map<String, Object> fields){
        Restaurant restaurantActual =  findById(id);
        merge(fields, restaurantActual);
        return update(id, restaurantActual);
    }

    private Restaurant verifyIfKitchenExist(Restaurant restaurant){
        Long idKitchen = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenService.findById(idKitchen);
        restaurant.setKitchen(kitchen);
        return restaurant;
    }

    private void merge(Map<String, Object> fields, Restaurant restaurantDestiny) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convertendo instancia com base no fields
        Restaurant restaurantOrigin = objectMapper.convertValue(fields, Restaurant.class);

        fields.forEach((nameProperty, valueProperty) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, nameProperty);
            field.setAccessible(true); // torna variavel acessivel

            Object newValue = ReflectionUtils.getField(field,restaurantOrigin);

            ReflectionUtils.setField(field, restaurantDestiny, newValue);

        });
    }
}
