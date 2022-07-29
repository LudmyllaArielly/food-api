set foreign_key_checks=0;

delete from city;
delete from form_of_payment;
delete from kitchen;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_form_of_payment;
delete from state;
delete from team;
delete from team_permission;
delete from user;
delete from user_team;

set foreign_key_checks=1;

alter table city auto_increment = 1;
alter table form_of_payment auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table state auto_increment = 1;
alter table team auto_increment = 1;
alter table user auto_increment = 1;


insert into kitchen (name) values ('Indiana');
insert into kitchen (name) values ('Italiana');
insert into kitchen (name) values ('Chinesa');
insert into kitchen (name) values ('Francesa');
insert into kitchen (name) values ('Brasileira');

insert into form_of_payment(description) values ("Cartão de crédito");
insert into form_of_payment(description) values ("Cartão de débito");
insert into form_of_payment(description) values ("Pix");
insert into form_of_payment(description) values ("Dinheiro");

insert into permission (name, description) values ('CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (name, description) values ('EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permission (name, description) values ('DELETAR_COZINHAS', 'Permite deletar cozinhas');

insert into team(name) values ("Users");
insert into team(name) values ("Clients");

insert into team_permission(team_id, permission_id) values(1,2);

insert into `user` (name, email, password, registration_date) values ("Maria", "maria@xyz.com", "333", utc_timestamp);
insert into `user` (name, email, password, registration_date) values ("José", "jose@xyz.com", "444", utc_timestamp);

insert into user_team(user_id, team_id) values (1,1), (2,2);

insert into state(id,name) values(1,"Rio de Janeiro");
insert into state(id,name) values(2,"Minas Gerais");
insert into state(id,name) values(3,"Goiás");

insert into city(name, state_id) values ("Juiz de fora",2);
insert into city(name, state_id) values ("Niterói",1);
insert into city(name, state_id) values ("Goiania",3);

insert into restaurant (address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date) values ("78456000", "Rua José Freire", "123", "Apto. 45", "Centro",3, "Sabor Brasil", 5.00, 1, utc_timestamp,utc_timestamp);
insert into restaurant (address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date) values ("78456000", "Rua José Freire", "123", "Apto. 45", "Centro",3, "Sabor Indiano", 12.00, 3, utc_timestamp,utc_timestamp);
insert into restaurant (address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date) values ("78456000", "Rua José Freire", "123", "Apto. 45", "Centro",2, "La pizza", 8.90, 4, utc_timestamp,utc_timestamp);
insert into restaurant (address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date) values ("78456000", "Rua José Freire", "123", "Apto. 45", "Centro",1, "Tukai Sahismi", 0.00, 2, utc_timestamp, utc_timestamp);
insert into restaurant (address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date) values ("78456000", "Rua José Freire", "123", "Apto. 45", "Centro",2, "Bar da Maria", 6.00, 2, utc_timestamp, utc_timestamp);

insert into restaurant_form_of_payment(restaurant_id, form_of_payment_id) values (1,1),(1,2), (2,4), (2,3), (3,2), (3,4), (4,1), (4,3), (4,4);

insert into product (name, description, price, active, restaurant_id) values ("Pudim de Laranja", "Lorem ipsum dolor sit amet.", 7.99, true, 1);
insert into product (name, description, price, active, restaurant_id) values ("Pudim de Chocolate", "Lorem ipsum dolor sit amet.", 9.99, false, 1);

insert into product (name, description, price, active, restaurant_id) values ("Sanduíche X-max", "Lorem ipsum dolor sit amet.", 12.50, true, 2);
insert into product (name, description, price, active, restaurant_id) values ("Pastel de Frango", "Lorem ipsum dolor sit amet.", 7.00, true, 2);

insert into product (name, description, price, active, restaurant_id) values ("Pizza 4 Queijos", "Lorem ipsum dolor sit amet.", 29.50, true, 3);
insert into product (name, description, price, active, restaurant_id) values ("Salgados de Carne", "Lorem ipsum dolor sit amet.", 6.90, true, 4);

insert into product (name, description, price, active, restaurant_id) values ("Marmita X", "Lorem ipsum dolor sit amet.", 13.90, true, 5);