    create table form_of_payment (
       id bigint not null auto_increment,
        description varchar(60) not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8;
