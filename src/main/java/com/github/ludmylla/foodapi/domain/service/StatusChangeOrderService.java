package com.github.ludmylla.foodapi.domain.service;

import com.github.ludmylla.foodapi.domain.model.Order;
import com.github.ludmylla.foodapi.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusChangeOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void confirm(String codeId) {
        Order order = verifyIfOrderExist(codeId);
        order.confirmOrder();

        orderRepository.save(order);
    }

    @Transactional
    public void cancel(String codeId) {
        Order order = verifyIfOrderExist(codeId);
        order.cancelOrder();
    }

    @Transactional
    public void delivery(String codeId) {
        Order order = verifyIfOrderExist(codeId);
        order.deliveryOrder();
    }

    private Order verifyIfOrderExist(String codeId) {
        return orderService.findById(codeId);
    }

}
