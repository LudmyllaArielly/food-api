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
delete from `user`;
delete from user_team;
delete from restaurant_user_responsible;
delete from `order`;
delete from items_order;

set foreign_key_checks=1;

alter table city auto_increment = 1;
alter table form_of_payment auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table state auto_increment = 1;
alter table team auto_increment = 1;
alter table `user` auto_increment = 1;
alter table `order` auto_increment = 1;
alter table items_order auto_increment = 1;

insert into kitchen (name) values ('Indiana');
insert into kitchen (name) values ('Italiana');
insert into kitchen (name) values ('Chinesa');
insert into kitchen (name) values ('Francesa');
insert into kitchen (name) values ('Brasileira');

insert into form_of_payment(id, description) values (1, "Cartão de crédito");
insert into form_of_payment(id, description) values (2, "Cartão de débito");
insert into form_of_payment(id, description) values (3, "Pix");
insert into form_of_payment(id, description) values (4, "Dinheiro");

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
insert into permission (id, name, description) values (3, 'DELETAR_COZINHAS', 'Permite deletar cozinhas');

insert into team(id, name) values (1, 'Admin'), (2, 'User'), (3, 'recorder');

insert into team_permission(team_id, permission_id) values(1,1), (1,2), (1,3), (2,1), (3, 1), (3,2);

insert into user (id, name, email, password, registration_date) values (1, "Maria", "maria@xyz.com", "333", utc_timestamp);
insert into user (id, name, email, password, registration_date) values (2, "Jones", "jones@xyz.com", "444", utc_timestamp);

insert into user_team(user_id, team_id) values (1,1), (2,2);

insert into state(id,name) values(1,"Rio de Janeiro");
insert into state(id,name) values(2,"Minas Gerais");
insert into state(id,name) values(3,"Goiás");

insert into city(id, name, state_id) values (1, "Juiz de fora",2);
insert into city(id, name, state_id) values (2, "Niterói",1);
insert into city(id, name, state_id) values (3, "Goiania",3);

insert into restaurant (id,address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date, activated, `open`) values (1,"78456000", "Rua José Freire", "123", "Apto. 45", "Centro",3, "Sabor Brasil", 5.00, 1, utc_timestamp,utc_timestamp, true, true);
insert into restaurant (id,address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date, activated, `open`) values (2,"78456000", "Rua José Freire", "123", "Apto. 45", "Centro",3, "Sabor Indiano", 12.00, 3, utc_timestamp,utc_timestamp, true, true);
insert into restaurant (id,address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date, activated, `open`) values (3,"78456000", "Rua José Freire", "123", "Apto. 45", "Centro",2, "La pizza", 8.90, 4, utc_timestamp,utc_timestamp, true, true);
insert into restaurant (id,address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date, activated, `open`) values (4,"78456000", "Rua José Freire", "123", "Apto. 45", "Centro",1, "Tukai Sahismi", 0.00, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id,address_zip_code, address_public_place, address_number,address_complement, address_district, address_city_id, name, freight_rate, kitchen_id, registration_date, update_date, activated, `open`) values (5,"78456000", "Rua José Freire", "123", "Apto. 45", "Centro",2, "Bar da Maria", 6.00, 2, utc_timestamp, utc_timestamp, true, true);

insert into restaurant_form_of_payment(restaurant_id, form_of_payment_id) values (1,1),(1,2), (2,4), (2,3), (3,2), (3,4), (4,1), (4,3), (4,4);

insert into restaurant_user_responsible (restaurant_id, user_id) values (1, 2), (3, 2);

insert into product (id, name, description, price, active, restaurant_id) values (1, "Pudim de Laranja", "Lorem ipsum dolor sit amet.", 7.99, true, 1);
insert into product (id, name, description, price, active, restaurant_id) values (2, "Pudim de Chocolate", "Lorem ipsum dolor sit amet.", 9.99, false, 1);

insert into product (id, name, description, price, active, restaurant_id) values (3, "Sanduíche X-max", "Lorem ipsum dolor sit amet.", 12.50, true, 2);
insert into product (id, name, description, price, active, restaurant_id) values (4, "Pastel de Frango", "Lorem ipsum dolor sit amet.", 7.00, true, 2);

insert into product (id, name, description, price, active, restaurant_id) values (5, "Pizza 4 Queijos", "Lorem ipsum dolor sit amet.", 29.50, true, 3);
insert into product (id ,name, description, price, active, restaurant_id) values (6, "Salgados de Carne", "Lorem ipsum dolor sit amet.", 6.90, true, 4);

insert into product (id, name, description, price, active, restaurant_id) values (7, "Marmita X", "Lorem ipsum dolor sit amet.", 13.90, true, 5);


insert into `order` (id, subtotal, freight_rate, price_total, status, creation_date, user_client_id, form_of_payment_id, restaurant_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city_id) values (1, 26.50, 10.00, 36.50, 'CREATED', utc_timestamp, 1, 1, 2, '74123000', 'Rua Jones 14', '458', 'Apt. 45', 'Setor 1', 3);

insert into items_order (id, quantity, unit_price, total_price, observation, product_id, order_id) values (1, 2, 7.00, 14.00, 'Loren', 4, 1);

insert into items_order (id, quantity, unit_price, total_price, observation, product_id, order_id) values (2, 1, 12.50, 12.50, 'Loren', 3, 1);

insert into `order` (id, subtotal, freight_rate, price_total, status, creation_date, user_client_id, form_of_payment_id, restaurant_id, address_zip_code, address_public_place, address_number, address_complement, address_district, address_city_id) values (2, 13.90, 10.00, 23.90, 'CREATED', utc_timestamp, 2, 1, 3, '78456000', 'Rua Jp 52', '78', 'Apt. 12', 'Setor 3', 2);

insert into items_order (id, quantity, unit_price, total_price, observation, product_id, order_id) values (3, 1, 13.90, 13.90, 'Loren', 7, 2);