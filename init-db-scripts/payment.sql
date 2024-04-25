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
