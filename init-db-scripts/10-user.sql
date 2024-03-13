
-- bang user
drop table if exists USER_USER;
CREATE TABLE USER_USER
(
    id           		BIGSERIAL primary key ,
    username     		VARCHAR UNIQUE NOT NULL,
    email        		VARCHAR,
    phone_number 		VARCHAR,
    password     		VARCHAR,
    first_name   		VARCHAR not null,
    last_name    		VARCHAR not null,
    birth_date    		date,
    avatar_url 	 		text,
    address      		VARCHAR,
    created_date 		date default now(),
    updated_date 		date,
    deleted_date 		date	
);
insert into user_user (username, email, first_name, last_name)
values ('dev', 'dev@app.local', 'Dev', 'Developer');


--
-- -- bang bai dang
-- drop table if exists user_post;
-- CREATE TABLE user_post(
--     id 				BIGSERIAL PRIMARY KEY ,
--     id_document		text,
--     user_id 		TEXT NOT NULL,
--     maximum_rental 	NUMERIC, -- số ngày mượn tối đa
--     description 	TEXT,
--     quantity		NUMERIC,
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
--
--
-- -- bang dau sach
-- drop table if exists dau_sach;
-- CREATE TABLE dau_sach(
-- 	id				BIGSERIAL PRIMARY KEY,  -- id 2
-- 	ISBN 			text, -- id
--     title 			text, --tieu de
--     authors 		text[],--tac gia
--     language 		text, --ngon ngu
--     genre 			text[], --id the loai
--     published 		text, -- nha xuat ban
--     published_date 	text, -- ngay phat hanh
--     page_count 		text, --so trang
--    	size			text,
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
-- --insert into dau_sach (id, ISBN , authors)
-- --values ('1111', 'devapplocal', '{Dev}');
--
--
--
--
--
-- -- su dung du lieu tu day https://www.kaggle.com/datasets/saurabhbagchi/books-dataset?resource=download
-- -- ket hop voi day https://github.com/avg-gh-enjoyer/bookstore-dataset/blob/master/bookDesc.csv
-- -- booklist.csv
--
-- -- loai tai lieu
-- drop table if exists doc_type;
-- CREATE TABLE doc_type(
--     id 				integer PRIMARY KEY,
--    	name_typ 		TEXT NOT NULL,
--    	description		text,
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
--
-- --drop table if exists doc_type;
-- --CREATE TABLE doc_type(
-- --    id 				BIGSERIAL PRIMARY KEY ,
-- --    password 		TEXT NOT NULL,
-- --    created_date 	date default now(),
-- --    updated_date 	date,
-- --    deleted_date 	date
-- --);
--
--
-- -- tài
-- drop table if exists document;
-- CREATE TABLE document(
--     id 				BIGSERIAL	PRIMARY KEY, -- id
--     id_dau_sach 	text, -- id dau sach
--     id_user 		text, -- id user
--     quantity		NUMERIC, -- so luong
--     img_link	 	text,--bang anh
--     price	 		DECIMAL(10, 2), --giá
--     percent_damage 	NUMERIC, --phan tram hu hai
--     deposit	 		DECIMAL(10, 2),-- tien coc
--     penalty_fee		DECIMAL(10, 2),-- phí phạt trả tre
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
--
-- --danh gia don hang
-- drop table if exists review;
-- CREATE TABLE review(
--     id 				BIGSERIAL 	PRIMARY KEY,
--     score 			real,
--     description 	TEXT,
--     link_img 		text[],
--     id_order		VARCHAR,
--     id_user			VARCHAR,
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
--
-- insert into review (score, description,id_order,id_user)
-- values (5, 'tốt', '1', '1')
--
-- --bang chua anh
-- --drop table if exists image;
-- --CREATE TABLE image(
-- --    id 				BIGSERIAL PRIMARY KEY,
-- --    img 			BYTEA,
-- --    description		text,
-- --    created_date 	date default now(),
-- --    updated_date 	date,
-- --    deleted_date 	date
-- --);
--
-- -- hình ảnh avatar default
-- -- INSERT INTO image (img, description)
-- -- VALUES (
-- --     pg_read_file('/Users/thao/Desktop/anh.png'), 'default'
-- -- );
--
--
--
--
-- drop table if exists orders;
-- CREATE TABLE orders(
--     id 					BIGSERIAL PRIMARY key,
--     id_user 			text, -- id user đi thuê
--     id_status			integer, -- id trạng thái đơn hàng
--     lessor_address		text, -- địa chỉ bên cho thuê
--     lessee_address		text, -- địa chỉ bên thuê
--     quantity			INTEGER, -- số lượng sản phẩm
--     refund_amount		DECIMAL(10, 2),-- số tiền hoàn
--     penalty_fee			DECIMAL(10, 2),-- tổng phí phạt trả trễ
--     total_deposit		DECIMAL(10, 2),-- tổng tiền cọc
--     payment_method		text,-- phương thức thanh toán
--     rental_fee			DECIMAL(10, 2)-- phí thuê tính đến thời điểm hiện tại
-- );
--
-- insert into review (score, description,id_order,id_user)
-- values (5, 'tốt', '1', '1')
--
-- drop table if exists order_detail;
-- CREATE TABLE order_detail(
--     id 					BIGSERIAL 	PRIMARY KEY,
--     id_order			text,
--     id_document			text,
--     count_day_overdue	integer, -- tính số ngày quá hạn
--     return_date			date, -- ngày trả sách
--     reciept_date		date, -- ngày nhận sách
--     created_date 		date default now(), -- ngày đặt
--     payment_date 		date, -- ngày thanh toán
--     refund_date			date, -- ngày hoàn tiền
--     quantity			INTEGER, -- số lượng
--     penalty_fee			DECIMAL(10, 2),-- tổng phí phạt trả trễ
--     deposit				DECIMAL(10, 2),-- tổng tiền cọc
--     refund_amount		DECIMAL(10, 2),-- số tiền hoàn
--     rental_fee			DECIMAL(10, 2),-- phí thuê tính đến thời điểm hiện tại
--     updated_date 		date,
--     deleted_date 		date
-- );
--
-- insert into review (id_order,id_document,count_day_overdue,return_date,reciept_date,payment_date,
--     refund_date,quantity,penalty_fee,deposit,refund_amount,rental_fee)
-- values (5, 'tốt', '1', '1')
--
-- --drop table if exists order_detail;
-- --CREATE TABLE order_detail(
-- --    id 				BIGSERIAL 	PRIMARY key,
-- --  	id_order		text, -- id đơn hàng
-- --  	id_doc			text, -- id tài liệu
-- --    rental_fee		DECIMAL(10, 2),-- phí thuê tính đến thời điểm hiện tại cho từng sản phẩm
-- --    quantity		INTEGER, -- số lượng
-- --    payment_date 	date, -- ngày thanh toán
-- --    return_date		date, -- ngày trả sách
-- --    reciept_date	date, -- ngày nhận sách
-- --    penalty_fee		DECIMAL(10, 2),-- tổng phí phạt trả trễ tính cho từng sản
-- --    id_order		text,
-- --    id_user			text,
-- --    created_date 	date default now(),
-- --    payment_method	text,-- phương thức thanh toán
-- --    rental_address	text,-- địa chỉ thuê
-- --    quantity		INTEGER, -- số lượng
-- --    updated_date 	date,
-- --    deleted_date 	date
-- --);
--
-- drop table if exists status_order;
-- CREATE TABLE status_order(
--     id 				integer	PRIMARY KEY,
--     name_status		text,
--     description		text,
--     created_date 	date default now(),
--     updated_date 	date,
--     deleted_date 	date
-- );
--
--
-- COPY (SELECT * from status_order)
-- TO 'status_order.csv'
-- (FORMAT CSV, HEADER TRUE, DELIMITER ',', ENCODING 'UTF8');
--
--
-- COPY (SELECT * from status_order)
-- TO '/Users/thao/Documents/status_order.csv';
--
--
-- COPY status_order
-- FROM 'status_order.csv'
-- DELIMITER ',' CSV HEADER;
--
-- -- INSERT INTO status_order (id, name_status, description)
-- --     VALUES (2,'test','test');
-- --insert into user_user (username, email, first_name, last_name)
-- --values ('dev', 'dev@app.local', 'Dev', 'Developer');
--
--




drop table if exists user_password;
CREATE TABLE user_password(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    password TEXT NOT NULL,
    created_date date default now(),
    updated_date date,
    deleted_date date
);
