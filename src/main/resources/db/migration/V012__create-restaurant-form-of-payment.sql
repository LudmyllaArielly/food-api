 create table restaurant_form_of_payment (
       restaurant_id bigint not null,
        form_of_payment_id bigint not null
    )  engine=InnoDB default charset=utf8;

 alter table restaurant_form_of_payment
       add constraint fk_restaurant_payment_form_of_payment_id
       foreign key (form_of_payment_id)
       references form_of_payment (id);

    alter table restaurant_form_of_payment
       add constraint fk_restaurant_payment_restaurant_id
       foreign key (restaurant_id)
       references restaurant (id);
