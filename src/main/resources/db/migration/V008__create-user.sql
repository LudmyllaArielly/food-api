create table user (
       id bigint not null auto_increment,
        email varchar(100) not null,
        name varchar(80) not null,
        password varchar(80) not null,
        registration_date datetime not null,
        primary key (id)
    )  engine=InnoDB default charset=utf8;