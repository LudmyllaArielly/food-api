package com.github.ludmylla.foodapi.domain.model;

import com.github.ludmylla.foodapi.domain.service.exceptions.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "`order`")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal priceTotal;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime cancellationDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_of_payment_id" ,nullable = false)
    private FormOfPayment formOfPayment;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" ,nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade =  CascadeType.ALL)
    private List<ItemsOrder> itemsOrders = new ArrayList<>();

    public void computePriceTotal() {
        getItemsOrders().forEach(ItemsOrder::computeTotalPrice);

        this.subtotal = getItemsOrders().stream()
                .map(item -> item.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.priceTotal = this.subtotal.add(this.freightRate);
    }

    public void confirmOrder(){
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());
    }

    public void deliveryOrder(){
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancelOrder(){
        setStatus(OrderStatus.CANCELED);
        setCancellationDate(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus newStatus){
        if(getStatus().cannotChangeTo(newStatus)){
            throw new BusinessException(String.format("Order status %s cannot be changed from %s to %s", getCode(), getStatus().getDescription(),
                   newStatus.getDescription()));
        }

        this.status = newStatus;
    }

    @PrePersist
    private void generateCode(){
        setCode(UUID.randomUUID().toString());
    }
}
