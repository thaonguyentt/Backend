-- Cart
drop table if exists cart;
create table cart (
    user_id bigint primary key ,
    items jsonb -- chứa sách và số lượng ??
)