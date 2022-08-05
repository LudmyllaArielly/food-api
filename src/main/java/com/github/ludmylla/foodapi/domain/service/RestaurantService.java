package com.github.ludmylla.foodapi.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ludmylla.foodapi.api.assembler.RestaurantInputDisassembler;
import com.github.ludmylla.foodapi.core.validation.ValidationException;
import com.github.ludmylla.foodapi.domain.exceptions.RestaurantNofFoundException;
import com.github.ludmylla.foodapi.domain.model.City;
import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.RestaurantRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private CityService cityService;

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private RestaurantInputDisassembler restaurantInputDisassembler;

    @Transactional
    public Restaurant create(Restaurant restaurant){
        verifyIfKitchenExist(restaurant);
        verifyIfCityExist(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll(){
        List<Restaurant> list = restaurantRepository.findAll();
        return list.stream().distinct().sorted(Comparator.comparing(Restaurant::getId))
                .collect(Collectors.toList());
    }

    public Restaurant findById(Long id){
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNofFoundException(id));
    }

    public Restaurant update(Long id, Restaurant restaurant){
        verifyIfKitchenExist(restaurant);
        verifyIfCityExist(restaurant);
        Restaurant restaurantActual = findById(id);

        restaurant.setId(id);
        restaurant.setRegistrationDate(restaurantActual.getRegistrationDate());

        BeanUtils.copyProperties(restaurant, restaurantActual,
                "id","formOfPayment", "address", "registrationDate", "products");
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activated(Long id){
        Restaurant restaurantActual = findById(id);
        restaurantActual.activated();
    }

    @Transactional
    public void inactivated(Long id){
        Restaurant restaurantActual = findById(id);
        restaurantActual.inactivated();
    }

    public Restaurant partialUpdate(Long id, Map<String, Object> fields, HttpServletRequest request){
        Restaurant restaurantActual =  findById(id);
        merge(fields, restaurantActual,request);

        validate(restaurantActual, "restaurant");

        return update(id, restaurantActual);
    }

    private void validate(Restaurant restaurant, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant,objectName);
        smartValidator.validate(restaurant, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private Restaurant verifyIfKitchenExist(Restaurant restaurant){
        Long idKitchen = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenService.findById(idKitchen);
        restaurant.setKitchen(kitchen);
        return restaurant;
    }

    private Restaurant verifyIfCityExist(Restaurant restaurant){
        Long idCity = restaurant.getAddress().getCity().getId();
        City city = cityService.findById(idCity);
        restaurant.getAddress().setCity(city);
        return restaurant;
    }


    private void merge(Map<String, Object> fields, Restaurant restaurantDestiny,HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            throwExceptionWhenCreatingAnAttributeThatDoesExistOrIsIgnoredInJson(objectMapper);

            // Convertendo instancia com base no fields
            Restaurant restaurantOrigin = objectMapper.convertValue(fields, Restaurant.class);

            fields.forEach((nameProperty, valueProperty) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, nameProperty);
                field.setAccessible(true); // torna variavel acessivel

                Object newValue = ReflectionUtils.getField(field, restaurantOrigin);

                ReflectionUtils.setField(field, restaurantDestiny, newValue);

            });
        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }

    private void throwExceptionWhenCreatingAnAttributeThatDoesExistOrIsIgnoredInJson (ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }
}
