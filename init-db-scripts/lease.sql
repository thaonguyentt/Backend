-- Lease
drop table if exists LEASE;
create table LEASE (
    id                  bigserial primary key ,
    lessor_id           bigserial not null,
    lessee_id           bigserial not null,
    listing_id          bigserial,
    status_id           bigserial,
    created_date        date default now(),
    from_date           date, --ngày thuê đăng kí
    to_date             date, --ngày trả đăng kí (bấm trả sách, gia hạn thì sẽ cập nhật lại ngày này)
    receive_date        date, --ngày thuê thật, tức ngày sách tới tay người thuê
    return_date         date, --ngày trả thật, tức ngày sách trả về chủ sách
    book_name           text,
    lessor_address      text, -- TODO ??
    lessee_address      text, -- TODO ??
    price               DECIMAL(10, 2),
    penalty_fee         DECIMAL(10,2),
    deposit             DECIMAL(10,2),
    quantity            integer,
    payment_method      integer, -- 1: cod, 2: chuyen khoan, 3. vnpay
    link_img            text, -- anh chuyen khoan , anh chuyen tien cod
    payment_deposit_id  integer,
    payment_refund_id   integer, -- trả lại tiền thừa cho người thuê
    payment_pay_owner_id integer -- trả tiền thuê sách cho chủ thuê
);
insert into LEASE (lessor_id,lessee_id,listing_id,status_id,from_date,to_date,receive_date,
                   return_date,book_name,lessor_address,lessee_address,price,penalty_fee,deposit,quantity,
                   payment_method,link_img,payment_deposit_id,payment_refund_id,payment_pay_owner_id)
values (1,2,1,1,'2024/04/01','2024/05/01',null,null,'Wallis','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Hoàng Quốc Việt, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội',300.00,2400.00,224100,1,1,null,null,null,null),
       (2,1,2,1,'2024/04/01','2024/05/01',null,null,'From Potter''s Field','Đường Hoàng Quốc Việt, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',2800.00,4600.00,414800.00,1,1,null,null,null,null);

alter sequence genre_id_seq restart with 3;
drop table if exists status_order;
CREATE TABLE status_order(
                             id 				bigserial PRIMARY KEY,
                             name_status		text,
                             description		text,
                             created_date 	    date default now(),
                             updated_date 	    date,
                             deleted_date 	    date
);
INSERT INTO status_order (id,name_status,description)
VALUES
    (1,'đặt hàng','đơn hàng đã được đặt'),
    (2,'đã thanh toán','đơn hàng đã thanh toán cọc'),
    (3,'đã nhận sách','người thuê đã nhận được sách'),
    (4,'đã quá hạn','người thuê đã quá hạn cho thuê'),
    (5,'trả sách','người thuê đã nhấn nút trả sách trên web'),
    (6,'đã trả sách','người cho thuê đã được nhận lại được sách'),
    (7,'đã hoàn tiền','người thuê sách đã được hoàn lại tiền'),
    (8,'đã trả tiền','người cho thuê sách đã nhận được tiền thuê');

-- -- Lease
-- drop table if exists LEASE;
-- create table LEASE (
--     id bigserial primary key ,
--     lessor_id bigserial not null,
--     lessee_id bigserial not null,
--     lessor_address text, -- TODO ??
--     lessee_address text, -- TODO ??
--     refund_amount DECIMAL(10,2),
--     penalty_fee DECIMAL(10,2),
--     total_deposit DECIMAL(10,2)
-- );
--
-- Lease_detail
-- drop table if exists LEASE_DETAIL;
-- create table LEASE_DETAIL(
--     id              bigserial primary key ,
--     lease_id        bigserial references LEASE(id) NOT NULL,
--
--     receive_date date,
--     return_date date,
--     deposit_amount decimal(10,2),
--     penalty_daily_rate decimal(10,2), -- TODO phí phạt mỗi ngày
--     penalty_amount decimal(10,2), -- TODO dùng cái trên thì cái này ko cần
--     refund_amount decimal(10,2),
--     rent_rate decimal(10,2), -- TODO phí thuê mỗi ngày => tính ra nhân lên
--     created_date date default now(),
--     updated_date date,
--     deleted_date date
-- );

-- Lease_review ?? Where do reviews live ?
drop table if exists REVIEW;
create table REVIEW (
    id bigserial primary key ,
    score real,
    description text,
    image_link  text[],
    lease_id bigserial references LEASE(id) not null,
    user_id bigserial not null,
    created_date timestamp default now(),
    updated_date date,
    deleted_date date
);