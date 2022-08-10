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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Boolean activated = Boolean.TRUE;

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

    private Boolean open = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "restaurant_form_of_payment",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "form_of_payment_id"))
    private Set<FormOfPayment> formOfPayments = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurant_user_responsible",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> responsible = new HashSet<>();

    public void activated() {
        setActivated(true);
    }

    public void inactivated() {
        setActivated(false);
    }

    public boolean removeFormOfPayment(FormOfPayment formOfPayment) {
        return getFormOfPayments().remove(formOfPayment);
    }

    public boolean addFormOfPayment(FormOfPayment formOfPayment) {
        return getFormOfPayments().add(formOfPayment);
    }

    public void open(){
        setOpen(true);
    }

    public void close(){
        setOpen(false);
    }

    public boolean addResponsible (User user){
        return getResponsible().add(user);
    }

    public boolean removeResponsible (User user){
        return getResponsible().remove(user);
    }

    public boolean acceptFormOfPayment(FormOfPayment formOfPayment){
        return getFormOfPayments().contains(formOfPayment);
    }

    public boolean doNotAcceptFormOfPayment(FormOfPayment formOfPayment){
        return !acceptFormOfPayment(formOfPayment);
    }

}