package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.model.OrderStatus;
import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class StatusChangeOrderService {

    @Autowired
    private OrderService orderService;

    @Transactional
    public void confirm(Long orderId){
        Order order = verifyIfOrderExist(orderId);

       checkIfStatusWasDifferentFromCreate(orderId, OrderStatus.CONFIRMED);

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = verifyIfOrderExist(orderId);

        checkIfStatusWasDifferentFromCreate(orderId, OrderStatus.CANCELED);

        order.setStatus(OrderStatus.CANCELED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void delivery(Long orderId){
        Order order = verifyIfOrderExist(orderId);

        if(!order.getStatus().equals(OrderStatus.CONFIRMED)){
            throw new BusinessException(String.format("Order status %d cannot be changed from %s to %s", order.getId(), order.getStatus().getDescription(),
                    OrderStatus.DELIVERED.getDescription()));
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    private Order verifyIfOrderExist(Long orderId){
       return orderService.findById(orderId);
    }

    private void checkIfStatusWasDifferentFromCreate (Long orderId, OrderStatus status) {
        Order order = verifyIfOrderExist(orderId);
        if(!order.getStatus().equals(OrderStatus.CREATED)){
            throw new BusinessException(String.format("Order status %d cannot be changed from %s to %s", order.getId(), order.getStatus().getDescription(),
                    status.getDescription()));
        }
    }
}
