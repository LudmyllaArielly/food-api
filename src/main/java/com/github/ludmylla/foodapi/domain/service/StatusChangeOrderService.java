package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusChangeOrderService {

    @Autowired
    private OrderService orderService;

    @Transactional
    public void confirm(Long orderId){
        Order order = verifyIfOrderExist(orderId);
        order.confirmOrder();
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = verifyIfOrderExist(orderId);
        order.cancelOrder();
    }

    @Transactional
    public void delivery(Long orderId){
        Order order = verifyIfOrderExist(orderId);
        order.deliveryOrder();
    }

    private Order verifyIfOrderExist(Long orderId){
       return orderService.findById(orderId);
    }

}
