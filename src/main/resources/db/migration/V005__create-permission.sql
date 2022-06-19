   create table permission (
       id bigint not null auto_increment,
        description varchar(255) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;