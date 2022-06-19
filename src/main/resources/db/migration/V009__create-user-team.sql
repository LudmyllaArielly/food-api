 create table user_team (
       user_id bigint not null,
		team_id bigint not null
    ) engine=InnoDB default charset=utf8;

 alter table user_team
       add constraint fk_team_id
       foreign key (team_id)
       references team (id);

    alter table user_team
       add constraint fk_user_id
       foreign key (user_id)
       references user (id);
