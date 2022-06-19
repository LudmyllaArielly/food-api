 create table restaurant (
       id bigint not null auto_increment,
        address_complement varchar(255),
        address_district varchar(100),
        address_number varchar(20),
        address_public_place varchar(100),
        address_zip_code varchar(8),
        freight_rate decimal(19,2) not null,
        name varchar(80) not null,
        registration_date datetime not null,
        update_date datetime not null,
        address_city_id bigint,
        kitchen_id bigint not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8;


     alter table restaurant
       add constraint fk_address_city_id
       foreign key (address_city_id)
       references city (id);

    alter table restaurant
       add constraint fk_kitchen_id
       foreign key (kitchen_id)
       references kitchen (id);