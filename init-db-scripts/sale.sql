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
    total_price             DECIMAL(10, 2),     -- giá cuốn sách
    total_change            DECIMAL(10, 2),     -- tiền thối lại cho người mua khi còn dư tiền cọc, đơn bán từ đơn thuê, là 0đ nếu là đơn bán từ sách ko có đơn thuê
    total_compensate        DECIMAL(10, 2),     -- tiền trả thêm khi người thuê muốn mua sách nhưng cọc ko đủ (trừ phí cọc và phí thuê, số tiền còn lại không đủ để mua cuốn sách đó nên phải bù thêm tiền)
    payment_method          integer,            -- 1. COD, 2. VNPAY, 3. BANK_TRANSFER
    sell_payment_id         integer default null, -- id payment người mua trả tiền cho cuốn sách, với đơn hàng xuất phát từ sách đang không có đơn thuê.
    change_payment_id       integer default null, -- id payment tiền thối lại cho người mua chuyển từ đơn hàng thuê qua đơn mua
    compensate_payment_id   integer default null, -- id payment tiền trả thêm khi phí cọc không đủ để mua cuốn sách mà user đang thuê
    created_date            date default now(),
    updated_date            date,
    deleted_date            date
);

insert into sales_order (id,listing_id,status,seller_id,buyer_id,seller_address,buyer_address,
                        receive_date,total_price,total_change,total_compensate,payment_method,
                        sell_payment_id,change_payment_id,compensate_payment_id,created_date)
values
    -- đơn bán không xuất phát từ đơn thuê
    (1,37,'ORDERED_PAYMENT_PENDING',1,2,'123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',null,235480.0,0.0,0.0,1,20,null,null,'2024/11/16'),
    (2,13,'CANCELED',1,2,'123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',null,175700.0,0.0,0.0,1,21,null,null,'2024/09/12'),
    (3,13,'PAYMENT_SUCCESS',1,2,'123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',null,175700.0,0.0,0.0,1,22,null,null,'2024/11/15'),
    (4,14,'DELIVERED',1,2,'123 Nguyễn Trọng Tuyển, phường 12, quận Bình Thạnh','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội',null,161630.0,0.0,0.0,1,23,null,null,'2024/11/12'),
 --  đơn bán xuất phát từ đơn thuê
    (5,4,'ORDERED_PAYMENT_PENDING',2,3,'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội','phố minh khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội',null,268590.0,36.590,0.0,1,null,null,24,'2024/11/12');
--     (6,3,'DELIVERED',2,1,'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội','phố minh khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội',null,268590.0,0.0,6000.0,1,null,25,null,'2024/11/12');
--     (7,3,'PAID_OWNER',2,1,'Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội','phố minh khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội',null,268590.0,0.0,6000.0,1,null,26,null,'2024/11/12');
-- đơn hàng số 7 dữ liệu sai chỉ để test




alter sequence sales_order_id_seq restart with 11;


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
    (1,'To Die for',1,37,13,235480.0),
    (2,'Vanishing Point',2,13,14,175700.0),
    (3,'Vanishing Point',3,13,14,175700.0),
    (4,'Hide and Seek',4,14,15,175700.0),
    (5,'Foundling',5, 4, 4, 2600.00);
--     (6,'Foundling',6, 4, 4, 2600.00);
--     (7,'Foundling',7, 4, 4, 2600.00);


alter sequence sales_order_detail_id_seq restart with 11;

