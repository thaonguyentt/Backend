drop table if exists lease_order_status;
CREATE TABLE lease_order_status
(
    id           bigserial PRIMARY KEY,
    name_status  text,
    description  text,
    created_date date default now(),
    updated_date date,
    deleted_date date
);
INSERT INTO lease_order_status (id, name_status, description)
VALUES (1, 'đặt hàng', 'đơn hàng đã được đặt'),
       (2, 'đã thanh toán', 'đơn hàng đã thanh toán cọc'),
       (3, 'đã nhận sách', 'người thuê đã nhận được sách'),
       (4, 'đã quá hạn', 'người thuê đã quá hạn cho thuê'),
       (5, 'trả sách', 'người thuê đã nhấn nút trả sách trên web'),
       (6, 'đã trả sách', 'người cho thuê đã được nhận lại được sách'),
       (7, 'đã hoàn tiền', 'người thuê sách đã được hoàn lại tiền'),
       (8, 'đã trả tiền', 'người cho thuê sách đã nhận được tiền thuê');


-- Lease
drop table if exists lease_order;
create table lease_order
(
    id                   bigserial primary key,
    listing_id           bigint,
    status_id            bigint references lease_order_status(id),
    lessor_id            bigint not null,
    lessee_id            bigint not null,
    lessor_address       text,            -- TODO ??
    lessee_address       text,            -- TODO ??
    from_date            date   not null, --ngày thuê đăng kí
    to_date              date   not null, --ngày trả đăng kí (bấm trả sách, gia hạn thì sẽ cập nhật lại ngày này)
    receive_date         date,            --ngày thuê thật, tức ngày sách tới tay người thuê
    return_date          date,            --ngày trả thật, tức ngày sách trả về chủ sách
    total_lease_fee      DECIMAL(10, 2),
    total_penalty_rate   DECIMAL(10, 2),
    total_deposit        DECIMAL(10, 2),
    payment_method       integer,         -- 1: cod, 2: chuyen khoan, 3. vnpay
    image_link           text,            -- TODO review: anh chuyen khoan , anh chuyen tien cod
    deposit_payment_id   integer,
    refund_payment_id    integer,         -- trả lại tiền thừa cho người thuê
    pay_owner_payment_id integer,         -- trả tiền thuê sách cho chủ thuê

    created_date         date default now(),
    updated_date         date,
    deleted_date         date
);
insert into lease_order (lessor_id, lessee_id, listing_id, status_id, from_date, to_date, receive_date,
                         return_date, lessor_address, lessee_address, total_lease_fee, total_penalty_rate,
                         total_deposit,
                         payment_method, image_link, deposit_payment_id, refund_payment_id, pay_owner_payment_id)
values (1, 2, 1, 1, '2024/04/01', '2024/05/01', null, null,
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Hoàng Quốc Việt, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội', 300.00, 2400.00, 224100, 1, null, null, null,
        null),
       (2, 1, 2, 1, '2024/04/01', '2024/05/01', null, null,
        'Đường Hoàng Quốc Việt, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội', 2800.00, 4600.00, 414800.00, 1, null, null, null,
        null);

alter sequence lease_order_id_seq restart with 10;

drop table if exists lease_order_detail;
create table lease_order_detail
(
    id             bigserial primary key,
    lease_order_id bigint not null references lease_order (id),
    listing_id     bigint references listing (id),
    copy_id        bigint not null references copy (id),
    lease_rate     decimal(10, 2), -- phí thuê
    deposit_fee    decimal(10, 2), -- phí cọc
    penalty_rate   decimal(10, 2)  -- phí phạt trễ theo ngày (tổng = penalty_rate x số ngày trả trễ)
);

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
create table REVIEW
(
    id           bigserial primary key,
    score        int,
    description  text,
    image_link   text[],
    lease_order_id     bigserial references lease_order (id) not null,
    user_id      bigserial                       not null,
    created_date timestamp default now(),
    updated_date date,
    deleted_date date
);