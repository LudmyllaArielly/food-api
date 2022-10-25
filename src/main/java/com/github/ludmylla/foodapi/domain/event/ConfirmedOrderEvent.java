package com.github.ludmylla.foodapi.domain.event;

import com.github.ludmylla.foodapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmedOrderEvent {

    private Order order;
}
