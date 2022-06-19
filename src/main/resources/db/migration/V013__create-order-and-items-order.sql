create table `order` (
	id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
	freight_rate decimal(10,2) not null,
	price_total decimal(10,2) not null,

    user_client_id bigint not null,
    form_of_payment_id bigint not null,
    restaurant_id bigint not null,

	address_city_id bigint not null,
    address_complement varchar(255) not null,
	address_district varchar(100) not null,
	address_number varchar(20) not null,
	address_public_place varchar(100) not null,
	address_zip_code varchar(8) not null,

	status varchar(80) not null,
    creation_date datetime not null,
    confirmation_date datetime null,
    delivery_date datetime null,
    cancellation_date datetime null,

    primary key (id),

	constraint fk_order_address_city foreign key (address_city_id) references city (id),
	constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_client foreign key (user_client_id) references user (id),
    constraint fk_order_form_of_payment foreign key (form_of_payment_id) references form_of_payment (id)


)engine=InnoDB default charset=utf8;

create table itemsOrder(
	id bigint not null auto_increment,
	quantity integer not null,
    unit_price decimal(10,2) not null,
	total_price decimal(10,2) not null,
    observation varchar(80) not null,
    product_id bigint not null,
    order_id bigint not null,

    primary key (id),

    constraint fk_item_order_order foreign key (order_id) references `order` (id),
    constraint fk_item_order_product foreign key (product_id) references product (id)

)engine=InnoDB default charset=utf8;