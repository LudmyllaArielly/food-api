package com.github.ludmylla.foodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "items_order")
public class ItemsOrder {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    public void computeTotalPrice(){
        BigDecimal unitPrice = this.getUnitPrice();
        Integer quantity = this.getQuantity();

        if(unitPrice == null){
            unitPrice = BigDecimal.ZERO;
        }

        if(quantity == null){
            quantity = 0;
        }

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
    }

}
