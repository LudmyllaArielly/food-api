package com.github.ludmylla.foodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ludmylla.foodapi.core.validation.FreightRate;
import com.github.ludmylla.foodapi.core.validation.Groups;
import com.github.ludmylla.foodapi.core.validation.Multiple;
import com.github.ludmylla.foodapi.core.validation.ZeroValueIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @NotBlank
    private String name;

    @NotNull
    //@PositiveOrZero
    @Multiple(number = 5)
    @FreightRate
    private BigDecimal freightRate;

    @JsonIgnore
    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime registrationDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private LocalDateTime updateDate;

    /*
    * @Valid valida as propriedades de cozinha
    *
    * @ConvertGroup diz que quando for valida a cozinha
    * ao inves de usa o default, use o groups.createRestaurant.class
    *
    * //@JsonIgnoreProperties("hibernateLazyInitializer")
    * */

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
    @NotNull
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
