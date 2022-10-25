package com.github.ludmylla.foodapi.domain.listener;

import com.github.ludmylla.foodapi.domain.event.ConfirmedOrderEvent;
import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.service.SendingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ConfirmedOrderNotificationListener {

    @Autowired
    private SendingEmailService sendingEmailService;

    @TransactionalEventListener
    public void whenConfirmOrder(ConfirmedOrderEvent event) {

        Order order = event.getOrder();
        var message =
                SendingEmailService.Message.builder()
                        .topic(order.getRestaurant().getName() + "- Order confirm")
                        .body("order-confirm.html")
                        .variable("order", order)
                        .addressee(order.getUser().getEmail())
                        .build();

        sendingEmailService.send(message);
    }

}
