package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.*;
import com.github.ludmylla.foodapi.domain.repository.OrderRepository;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import com.github.ludmylla.foodapi.domain.service.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FormOfPaymentService formOfPaymentService;

    @Autowired
    private ProductService productService;

    public Order findById(String code){
        return orderRepository.findByCode(code)
                .orElseThrow(() -> new OrderNotFoundException(code));
    }

    @Transactional
    public Order issueOrder(Order order){
        validOrder(order);
        validItems(order);

        order.setFreightRate(order.getRestaurant().getFreightRate());
        order.computePriceTotal();

        return orderRepository.save(order);
    }

    private void validOrder(Order order){
        City city = cityService.findById(order.getAddress().getCity().getId());
        User user = userService.findById(order.getUser().getId());
        Restaurant restaurant = restaurantService.findById(order.getRestaurant().getId());
        FormOfPayment formOfPayment = formOfPaymentService.findById(order.getFormOfPayment().getId());

        order.getAddress().setCity(city);
        order.setUser(user);
        order.setFormOfPayment(formOfPayment);
        order.setRestaurant(restaurant);

        if(restaurant.doNotAcceptFormOfPayment(formOfPayment)){
            throw new BusinessException(String.format("Payment method '%s' is not accepted by this Restaurant", formOfPayment.getDescription()));
        }
    }

    private void validItems(Order order){
        order.getItemsOrders().forEach(item -> {
            Product product = productService.findByIdRestaurant(order.getRestaurant().getId(), item.getProduct().getId());

            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }
}
