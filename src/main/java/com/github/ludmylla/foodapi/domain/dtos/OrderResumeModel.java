package com.github.ludmylla.foodapi.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("orderFilter")
@Getter
@Setter
public class OrderResumeModel {

    private String code;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal priceTotal;
    private String status;

    private OffsetDateTime creationDate;

    private RestaurantResumeModel restaurant;
    private UserModel user;

}
