create table team_permission (
       team_id bigint not null,
		permission_id bigint not null
    )  engine=InnoDB default charset=utf8;

alter table team_permission
       add constraint fk_permission
       foreign key (permission_id)
       references permission (id);

alter table team_permission
       add constraint fk_team
       foreign key (team_id)
       references team (id);