package com.github.ludmylla.foodapi.api.controller;

import com.github.ludmylla.foodapi.domain.model.Kitchen;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.KitchenRepository;
import com.github.ludmylla.foodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/byName")
    public List<Kitchen> kitchensByName(@RequestParam String name){
        return kitchenRepository.findByName(name);
    }

    @GetMapping("/byNameLike")
    public List<Kitchen> kitchensByNameLike(@RequestParam String name){
        return kitchenRepository.findByNameContaining(name);
    }

    @GetMapping("/existsByName")
    public boolean existsByName(String name){
        return kitchenRepository.existsByName(name);
    }

    @GetMapping("/freightRateBetween")
    public List<Restaurant> findByFreightRateBetween(BigDecimal freightRateInitial, BigDecimal freightRateFinal){
        return restaurantRepository.findByFreightRateBetween(freightRateInitial, freightRateFinal);
    }

    @GetMapping("/findByNameLikeKitchenId")
    public List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId){
        return restaurantRepository.findByNameLikeAndKitchenId(name, kitchenId);
    }

    @GetMapping("/findFistName")
    public Restaurant findFirstName(String name){
        return restaurantRepository.findFirstByNameContaining(name);
    }

    @GetMapping("/findTop2ByName")
    public List<Restaurant> findTop2ByName(String name){
        return restaurantRepository.findTop2ByNameContaining(name);
    }

    @GetMapping("/findByNameLikeKitchenId2")
    public List<Restaurant> findByNameLikeKitchenId2(String name, BigDecimal freightRateInitial, BigDecimal freightRateFinal){
        return restaurantRepository.find(name, freightRateInitial, freightRateFinal);
    }

    @GetMapping("/countByKitchenId")
    public int countByKitchenId(Long kitchenId){
        return restaurantRepository.countByKitchenId(kitchenId);
    }

    @GetMapping("/sumByFreightRate")
    public BigDecimal sumByFreightRate(BigDecimal freightRate){
        return restaurantRepository.sumByFreightRate(freightRate);
    }

    @GetMapping("/findFreightRateFree")
    public List<Restaurant> findByRestaurantWithFreightRateFree(String name){
        return restaurantRepository.findWithFreightRateFree(name);
    }

    @GetMapping("/findFirst")
    public Optional<Restaurant> findFirst(){
        return restaurantRepository.findFirst();
    }


}
