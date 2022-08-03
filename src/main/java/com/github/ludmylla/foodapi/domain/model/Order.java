package com.github.ludmylla.foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal priceTotal;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "form_of_payment_id" ,nullable = false)
    private FormOfPayment formOfPayment;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" ,nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    private List<ItemsOrder> itemsOrders = new ArrayList<>();


}
