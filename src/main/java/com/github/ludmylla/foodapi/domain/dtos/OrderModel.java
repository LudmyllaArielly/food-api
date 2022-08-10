package com.github.ludmylla.foodapi.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal priceTotal;
    private String status;

    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;

    private RestaurantResumeModel restaurant;
    private UserModel user;
    private FormOfPaymentModel formOfPayment;
    private AddressModel address;
    private List<ItemsOrderModel> itemsOrders;
}
