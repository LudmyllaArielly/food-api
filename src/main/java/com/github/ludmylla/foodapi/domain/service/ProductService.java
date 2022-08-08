package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Product;
import com.github.ludmylla.foodapi.domain.model.Restaurant;
import com.github.ludmylla.foodapi.domain.repository.ProductRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantService restaurantService;


    @Transactional
    public Product create(Long restaurantId,Product product){
        Restaurant restaurant = restaurantService.findById(restaurantId);
        product.setRestaurant(restaurant);
        return productRepository.save(product);
    }

    public List<Product> findAllByRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantService.findById(restaurantId);
        return productRepository.findByRestaurant(restaurant);
    }

    public Product findByIdRestaurant(Long restaurantId, Long productId){
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId, restaurantId));
    }

    @Transactional
    public Product update(Long restaurantId, Long productId, Product product){
        Product productActual = findByIdRestaurant(restaurantId, productId);
        product.setId(productActual.getId());
        return productRepository.save(product);
    }
}
