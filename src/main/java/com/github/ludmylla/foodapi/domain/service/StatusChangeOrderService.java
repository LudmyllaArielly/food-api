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
    public void confirm(String codeId){
        Order order = verifyIfOrderExist(codeId);
        order.confirmOrder();
    }

    @Transactional
    public void cancel(String codeId){
        Order order = verifyIfOrderExist(codeId);
        order.cancelOrder();
    }

    @Transactional
    public void delivery(String codeId){
        Order order = verifyIfOrderExist(codeId);
        order.deliveryOrder();
    }

    private Order verifyIfOrderExist(String codeId){
       return orderService.findById(codeId);
    }

}
