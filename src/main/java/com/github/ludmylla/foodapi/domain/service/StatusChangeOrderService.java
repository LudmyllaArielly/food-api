package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StatusChangeOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SendingEmailService sendingEmailService;

    @Transactional
    public void confirm(String codeId){
        Order order = verifyIfOrderExist(codeId);
        order.confirmOrder();

        Set<String> addressees = new HashSet<>();

        var message =
                SendingEmailService.Message.builder()
                        .topic(order.getRestaurant().getName() + "- Order confirm")
                        .body("The code order <strong> " + order.getCode() + "</strong> has been confirmed!")
                        .addressee(order.getUser().getEmail())
                        .build();

        sendingEmailService.send(message);
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
