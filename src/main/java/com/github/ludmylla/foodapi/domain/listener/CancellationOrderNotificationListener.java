package com.github.ludmylla.foodapi.domain.listener;

import com.github.ludmylla.foodapi.domain.event.CancellationOrderEvent;
import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.service.SendingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CancellationOrderNotificationListener {

    @Autowired
    private SendingEmailService sendingEmailService;

    @TransactionalEventListener
    public void whenCancelOrder(CancellationOrderEvent event) {

        Order order = event.getOrder();
        var message =
                SendingEmailService.Message.builder()
                        .topic(order.getRestaurant().getName() + "- Order cancel")
                        .body("order-cancel.html")
                        .variable("order", order)
                        .addressee(order.getUser().getEmail())
                        .build();

        sendingEmailService.send(message);
    }

}
