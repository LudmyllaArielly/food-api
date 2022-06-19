create table product (
       id bigint not null auto_increment,
        active boolean not null,
        description varchar(255),
        name varchar(80) not null,
        price decimal(19,2) not null,
        restaurant_id bigint not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8;

    alter table product
       add constraint fk_restaurant_id
       foreign key (restaurant_id)
       references restaurant (id);
