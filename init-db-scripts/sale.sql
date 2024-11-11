-- CREATE SEQUENCE sale_order_id_seq START WITH 2 INCREMENT BY 1;
-- sale_order
drop table if exists sales_order;
create table sales_order
(
    id                      bigserial primary key,
    listing_id              bigint,
    status                  text,
    seller_id               bigint not null,
    buyer_id                bigint not null,
    seller_address          text,
    buyer_address           text,
    receive_date            date,
    total_price             DECIMAL(10, 2),
    total_change            DECIMAL(10, 2),  -- tiền trả dư lại cho người mua khi còn dư tiền cọc
    total_compensate        DECIMAL(10, 2),  -- tiền trả thêm khi người mua không đủ phí
    payment_method          integer,         -- 1. COD, 2. VNPAY, 3. BANK_TRANSFER, 4. crypto
    sell_payment_id         integer,
    change_payment_id       integer,         -- id payment trả lại tiền thừa cho người mua chuyển từ đơn hàng thuê qua
    compensate_payment_id   integer,
    created_date            date default now(),
    updated_date            date,
    deleted_date            date
);

insert into sales_order (id,listing_id,status,seller_id,buyer_id,seller_address,buyer_address,
                        receive_date,total_price,total_change,total_compensate,payment_method,
                        sell_payment_id,change_payment_id,compensate_payment_id,created_date)
values
    (1,37,'ORDERED_PAYMENT_PENDING',1,2,'test','test',null,100000.0,10000.0,0.0,1,1,1,null,'2024/09/12');
alter sequence sales_order_id_seq restart with 2;


-- CREATE SEQUENCE sale_order_detail_id_seq START WITH 2 INCREMENT BY 1;

drop table if exists sales_order_detail;
create table sales_order_detail
(
    id             bigserial primary key,
    title          text,
    sale_order_id  bigint not null references sales_order (id),
    listing_id     bigint references listing (id),
    copy_id        bigint not null references copy (id),
    price          decimal(10, 2)
);

insert into sales_order_detail (id,title,sale_order_id,listing_id,copy_id,price)
values
    (1,'test',1,1,1,100000.0),
    (2,'');

["https://i.ibb.co/9Z0j3Bg/Screen-Shot-2024-11-11-at-20-28-45.png",
"https://i.ibb.co/NSXFBM0/Screen-Shot-2024-11-11-at-20-29-12.png",
"https://i.ibb.co/2nwyddJ/Screen-Shot-2024-11-11-at-20-29-40.png",
"https://i.ibb.co/rxXnQR4/Screen-Shot-2024-11-11-at-20-29-53.png",
"https://i.ibb.co/FnGQXHS/Screen-Shot-2024-11-11-at-20-30-06.png",
"https://i.ibb.co/BN4Mbmw/Screen-Shot-2024-11-11-at-20-30-19.png",
"https://i.ibb.co/NKmZHcc/Screen-Shot-2024-11-11-at-20-30-32.png",
"https://i.ibb.co/GJT1134/Screen-Shot-2024-11-11-at-20-30-44.png",
"https://i.ibb.co/3FF3c1y/Screen-Shot-2024-11-11-at-20-30-55.png",
"https://i.ibb.co/cgKf6WS/Screen-Shot-2024-11-11-at-20-31-07.png",
"https://i.ibb.co/Sy6kD7c/Screen-Shot-2024-11-11-at-20-31-40.png",
"https://i.ibb.co/W6BWS51/Screen-Shot-2024-11-11-at-20-31-51.png",
"https://i.ibb.co/vsddnr8/Screen-Shot-2024-11-11-at-20-32-01.png",
"https://i.ibb.co/XJZLdXC/Screen-Shot-2024-11-11-at-20-32-13.png",
"https://i.ibb.co/5jsDnGJ/Screen-Shot-2024-11-11-at-20-32-25.png"]

alter sequence sales_order_detail_id_seq restart with 2;