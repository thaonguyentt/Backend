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
    user_id bigint references user_user(id)

)
