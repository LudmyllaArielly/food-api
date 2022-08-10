package com.github.ludmylla.foodapi.domain.dtos.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderInputModel {

    @Valid
    @NotNull
    private RestaurantIdInputModel restaurant;

    @Valid
    @NotNull
    private AddressInputModel deliveryAddress;

    @Valid
    @NotNull
    private FormOfPaymentIdInputModel formOfPayment;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemOrderInputModel> itemsOrders;
}
