package com.github.ludmylla.foodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal freightRate;

    @JsonIgnore
    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime registrationDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime updateDate;

    //@JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @JsonIgnore
    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurant_form_of_payment",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "form_of_payment_id"))
    private List<FormOfPayment> formOfPayments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

}
