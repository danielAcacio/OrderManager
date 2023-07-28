create table if not exists user_orderer(
	user_id serial not null,
	user_name varchar(200) not null,
	user_email varchar(100) not null unique,
	constraint user_pk primary key(user_id)
);

create table if not exists item(
	 item_id serial not null,
	 item_name varchar(200) not null,
	 constraint item_pk primary key(item_id)
);

create table if not exists stock_order(
	 order_id serial not null,
	 order_status varchar(20) not null,
	 quantity int4 not null,
	 item_id int4 not null,
	 user_id int4 not null,
	 creation_date timestamp not null,
	 constraint order_pk primary key(order_id),
	 constraint order_item_fk foreign key(item_id) references item(item_id),
	 constraint order_user_fk foreign key(user_id) references user_orderer(user_id)
);

create table if not exists stock_movement(
	stock_movement_id serial not null,
	creation_date timestamp not null,
	movement_type varchar(20),
	quantity int4 not null,
	item_id int4 not null,
	constraint stock_movement_pk primary key(stock_movement_id),
	constraint stock_movement_item_fk foreign key(item_id) references item(item_id)
);

create table if not exists order_stock_usage(
	order_stock_usage_id serial not null,
	order_id int4 not null,
	stock_movement_id int4 not null,
	quantity int4 not null,
	constraint pk_stock_usage primary key(order_stock_usage_id),
	constraint stock_order_usage_fk foreign key(order_id) references stock_order(order_id),
	constraint stock_movement_usagefk foreign key(stock_movement_id) references stock_movement(stock_movement_id)
);
