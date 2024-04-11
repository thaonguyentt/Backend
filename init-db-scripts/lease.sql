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
    status               text,
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
insert into lease_order (id,lessor_id, lessee_id, listing_id, status, from_date, to_date, receive_date,
                         return_date, lessor_address, lessee_address, total_lease_fee,
                         total_penalty_rate,total_deposit, payment_method, image_link,
                         deposit_payment_id, refund_payment_id, pay_owner_payment_id)
values (1, 1, 2, 1, 'ORDERED_PAYMENT_PENDING', '2024/05/01', '2024/06/01', null, null,
        '123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội', 90000.00, 0.00, 224100.00, 1, null, null, null,
        null),
       (2, 2, 1, 2, 'ORDERED_PAYMENT_PENDING', '2024/05/01', '2024/06/01', null, null,
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        '123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh', 840000.00, 0, 414800.00, 1, null, null, null,
        null),
       (3, 2, 1, 3, 'CANCELED', '2024/03/01', '2024/04/01', null, null,
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        '123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh', 42000.00, 0, 46000.00, 1, null, null, null,
            null),
       (4, 2, 1, 3, 'PAYMENT_SUCCESS', '2024/05/01', '2024/06/01', null, null,
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        '123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh', 42000.00, 0, 46000.00, 1, null, null, null,
        null),
       (5, 2, 3, 4, 'DELIVERED', '2024/05/01', '2024/06/01', '2024/05/01', null,
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'phố minh khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội', 78000.00, 0, 383700.00, 1, null, null, null,
        null),
       (6, 2, 4, 5, 'RETURNED', '2024/05/01', '2024/06/01', '2024/05/01', '2024/06/01',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội', 48000.00, 0, 376600.00, 1, null, null, null,
        null),
       (7, 2, 5, 6, 'LATE_RETURN', '2024/03/01', '2024/04/01', '2024/03/01', '2024/04/10',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Yên Hòa, Phường Yên Hoà, Quận Cầu Giấy, Hà Nội', 36000.00, 15000.00, 105700.00, 1, null, null, null,
        null),
       (8, 2, 6, 7, 'DEPOSIT_RETURNED', '2024/03/01', '2024/04/01', '2024/03/01', '2024/04/01',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Tây Sơn, Phường Trung Liệt, Quận Đống Đa, Hà Nội', 60000.00, 0, 120300.00, 1, null, null, null,
        null),
       (9, 2, 7, 8, 'PAID_OWNER', '2024/03/01', '2024/04/01', '2024/03/01', '2024/04/01',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Lò Đúc, Phường Đống Mác, Quận Hai Bà Trưng, Hà Nội', 42000.00, 0, 76500.00, 1, null, null, null,
        null),
       (10, 2, 8, 9, 'RETURNING', '2024/03/01', '2024/04/01', '2024/03/01', '2024/04/01',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',
        'Đường Xuân La, Phường Xuân La, Quận Tây Hồ, Hà Nội', 24000.00, 0, 194200.00, 1, null, null, null,
        null),
       (11, 1, 2, 1, 'RETURNED', '2024/02/01', '2024/03/01', '2024/02/05', '2024/03/01',
        '123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh',
        'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội', 9000.00, 0, 224100, 1, null, null, null,
        null);

alter sequence lease_order_id_seq restart with 12;

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
insert into lease_order_detail (lease_order_id, listing_id, copy_id, lease_rate, deposit_fee, penalty_rate)
values (1,1,1, 300.00, 224100.00, 2400.00),
       (2,2, 2, 2800.00, 414800.00, 4600.00),
       (3, 3, 3, 1400.00, 46000.00, 2000.00),
       (4, 3, 3, 1400.00, 46000.00, 2000.00),
       (5, 4, 4, 2600.00, 383700.00, 4200.00),
       (6, 5, 5, 1600.00, 376600.00, 4100.00),
       (7, 6, 6, 1200.00, 105700.00, 1500.00),
       (8, 7, 7, 2000.00, 120300.00, 2100.00),
       (9, 8, 8, 1400.00, 76500.00, 1500.00),
       (10, 9, 9, 800.00, 194200.00, 2100.00),
       (11, 1, 1, 300.00, 224100, 2400.00);

alter sequence lease_order_detail_id_seq restart with 12;

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
    id                  bigserial primary key,
    score               int,
    description         text,
    image_link          text[],
    lease_order_id      bigserial references lease_order (id) not null,
    user_id             bigserial not null,
    listing_id          bigserial,
    created_date        timestamp default now(),
    updated_date        date,
    deleted_date        date
);

insert into REVIEW (score, description,lease_order_id,user_id, listing_id,created_date)
values (5,'Sách đẹp.
Mình thích nhỏ xinh nên chọn lấy nhỏ.
Nhưng sách dễ mở, dễ đọc không lo bị lẹm chữ hay khó đọc phần gáy đâu nha mọi người',11,2,1,'2024/03/01'),
       (5,'Sách đẹp.
Mình thích nhỏ xinh nên chọn lấy nhỏ.
Nhưng sách dễ mở, dễ đọc không lo bị lẹm chữ hay khó đọc phần gáy đâu nha mọi người',6,5,6,'2024/04/10');