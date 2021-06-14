create table orders
(

    id            serial,
    price         float check (price > 0.0),
    quantity      int CHECK (quantity > 0 ),
    localDateTime date
);

alter table orders
    add primary key (id);

create table item
(
    id   serial,
    name varchar(50) not null,
    price float check (price > 0.0)
);
alter table item
    add primary key (id);

create table orders_item
(
    orders_id int not null,
    item_id   int not null
);
alter table orders_item
    add primary key (orders_id, item_id);
alter table orders_item
    add foreign key (orders_id) references orders (id);
alter table orders_item
    add foreign key (item_id) references item (id);