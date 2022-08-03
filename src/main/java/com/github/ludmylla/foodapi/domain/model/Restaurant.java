package com.github.ludmylla.foodapi.domain.model;

import com.github.ludmylla.foodapi.core.validation.ZeroValueIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ZeroValueIncludeDescription(valueField = "freightRate", descriptionField= "name", descriptionRequired= "Freight rate free")
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

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(name = "restaurant_form_of_payment",
    joinColumns = @JoinColumn(name = "restaurant_id"),
    inverseJoinColumns = @JoinColumn(name = "form_of_payment_id"))
    private List<FormOfPayment> formOfPayments = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

}
