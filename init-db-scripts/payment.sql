-- Invoice / Receipt ?
drop table if exists receipt;
create table receipt (
    id bigserial primary key not null,
    transaction_time date not null,
    amount decimal(10,2) not null

);

-- Payment
drop table if exists payment;
create table payment (
    id bigserial primary key not null ,
    payment_status text not null,
    payer_id bigint not null,
    payee_id bigint not null,
    currency text not null,
    amount bigint not null,
    payment_method text not null,
    description text,
    bank_transfer_img_url text
);

insert into payment (id, payment_status, payer_id, payee_id, currency, amount, payment_method)
values (1, 'PAYMENT_PENDING', 2, 0,'VND', 224100.00, 'COD'),
       (2, 'PAYMENT_PENDING', 1, 0,'VND', 414800.00, 'COD'),
       (3, 'PAYMENT_PENDING', 1, 0,'VND', 46000.00, 'COD'),
       (4, 'SUCCEEDED', 1, 0,'VND', 46000.00, 'COD'),
       (5, 'SUCCEEDED', 3, 0,'VND', 383700.00, 'COD'),
       (6, 'SUCCEEDED', 4, 0,'VND', 376600.00, 'COD'),
       (7, 'SUCCEEDED', 5, 0,'VND', 105700.00, 'COD'),
       (8, 'SUCCEEDED', 6, 0,'VND', 120300.00, 'COD'),
       (9, 'PAYMENT_PENDING', 7, 0,'VND', 76500.00, 'COD'),
       (10, 'SUCCEEDED', 8, 0,'VND', 194200.00, 'COD'),
       (11, 'SUCCEEDED', 2, 0,'VND', 224100.00, 'COD'),
       (12, 'SUCCEEDED', 0, 6,'VND', 84300.00, 'COD'),
       (13, 'SUCCEEDED', 0, 7,'VND', 16500.00, 'COD'),
       (14, 'SUCCEEDED', 0, 7,'VND', 60000.00, 'COD'),
       (15, 'PAYMENT_PENDING', 0, 4,'VND', 376600.00, 'COD'),
       (16, 'PAYMENT_PENDING', 0, 4,'VND', 298600.00, 'COD'),
       (17, 'SUCCEEDED', 0, 2,'VND', 140100.00, 'COD'),
       (18, 'SUCCEEDED', 0, 2,'VND', 8400.00, 'COD'),
       (19, 'PAYMENT_PENDING', 5, 0,'VND', 105700.00, 'COD'),
       (20, 'PAYMENT_PENDING', 2, 0,'VND', 235480.0, 'COD'),
       (21, 'PAYMENT_PENDING', 2, 0,'VND', 175700.0, 'COD'),
       (22, 'SUCCEEDED', 2, 0,'VND', 175700.0, 'COD'),
       (23, 'SUCCEEDED', 2, 0,'VND', 161630.0, 'COD'),
       (24, 'PAYMENT_PENDING', 3, 0,'VND', 16.590, 'COD'),
       (25, 'PAYMENT_PENDING', 0, 1,'VND', 16.590, 'COD'),
       (26, 'PAYMENT_PENDING', 0, 1,'VND', 16.590, 'COD');


alter sequence payment_id_seq restart with 30;