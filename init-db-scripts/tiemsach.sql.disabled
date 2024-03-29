-- bang bai dang
drop table if exists user_post;
CREATE TABLE user_post(
                          id 				BIGSERIAL PRIMARY KEY ,
                          id_document		text,
                          user_id 		    TEXT NOT NULL,
                          maximum_rental 	NUMERIC, -- số ngày mượn tối đa
                          description 	    TEXT,
                          quantity		    NUMERIC,
                          created_date 	    date default now(),
                          updated_date 	    date,
                          deleted_date 	    date
);


-- bang dau sach
drop table if exists dau_sach;
CREATE TABLE dau_sach(
                         id				BIGSERIAL PRIMARY KEY,  -- id 2
                         ISBN 			text, -- id
                         title 			text, --tieu de
                         authors 		text[],--tac gia
                         language 		text, --ngon ngu
                         genre 			text[], --id the loai
                         published 		text, -- nha xuat ban
                         published_date 	text, -- ngay phat hanh
                         page_count 		text, --so trang
                         size			text,
                         created_date 	date default now(),
                         updated_date 	date,
                         deleted_date 	date
);

-- loai tai lieu
drop table if exists doc_type;
CREATE TABLE doc_type(
                         id 				integer PRIMARY KEY,
                         name_typ 		TEXT NOT NULL,
                         description		text,
                         created_date 	date default now(),
                         updated_date 	date,
                         deleted_date 	date
);

INSERT INTO doc_type (id,name_typ, description)
VALUES
    (1,'đặt hàng','đơn hàng đã được đặt'),
    (2,'đã thanh toán','đơn hàng đã thanh toán cọc'),
    (3,'đã nhận sách','người thuê đã nhận được sách'),
    (4,'đã quá hạn','người thuê đã quá hạn cho thuê'),
    (5,'trả sách','người thuê đã nhấn nút trả sách trên web'),
    (6,'đã trả sách','người cho thuê đã được nhận lại được sách'),
    (7,'đã hoàn tiền','người thuê sách đã được hoàn lại tiền'),
    (8,'đã trả tiền','người cho thuê sách đã nhận được tiền thuê');

-- tài lieu
drop table if exists document;
CREATE TABLE document(
                         id 				BIGSERIAL	PRIMARY KEY, -- id
                         id_dau_sach 	text, -- id dau sach
                         id_user 		text, -- id user
                         quantity		NUMERIC, -- so luong
                         img_link	 	text,--bang anh
                         price	 		DECIMAL(10, 2), --giá
                         percent_damage 	NUMERIC, --phan tram hu hai
                         deposit	 		DECIMAL(10, 2),-- tien coc
                         penalty_fee		DECIMAL(10, 2),-- phí phạt trả tre
                         created_date 	date default now(),
                         updated_date 	date,
                         deleted_date 	date
);

--danh gia don hang
drop table if exists review;
CREATE TABLE review(
                       id 				BIGSERIAL 	PRIMARY KEY,
                       score 			real,
                       description 	    TEXT,
                       link_img 		text[],
                       id_order		    VARCHAR,
                       id_user			VARCHAR,
                       created_date 	date default now(),
                       updated_date 	date,
                       deleted_date 	date
);

insert into review (score, description,id_order,id_user)
values (5, 'tốt', '1', '1');





drop table if exists orders;
CREATE TABLE orders(
                       id 					BIGSERIAL PRIMARY key,
                       id_user 			text, -- id user đi thuê
                       id_status			integer, -- id trạng thái đơn hàng
                       lessor_address		text, -- địa chỉ bên cho thuê
                       lessee_address		text, -- địa chỉ bên thuê
                       quantity			INTEGER, -- số lượng sản phẩm
                       refund_amount		DECIMAL(10, 2),-- số tiền hoàn
                       penalty_fee			DECIMAL(10, 2),-- tổng phí phạt trả trễ
                       total_deposit		DECIMAL(10, 2),-- tổng tiền cọc
                       payment_method		text,-- phương thức thanh toán
                       rental_fee			DECIMAL(10, 2)-- phí thuê tính đến thời điểm hiện tại
);

drop table if exists order_detail;
CREATE TABLE order_detail(
                             id 					BIGSERIAL 	PRIMARY KEY,
                             id_order			    text,
                             id_document			text,
                             count_day_overdue	    integer, -- tính số ngày quá hạn
                             return_date			date, -- ngày trả sách
                             reciept_date		    date, -- ngày nhận sách
                             created_date 		    date default now(), -- ngày đặt
                             payment_date 		    date, -- ngày thanh toán
                             refund_date			date, -- ngày hoàn tiền
                             quantity			    INTEGER, -- số lượng
                             penalty_fee			DECIMAL(10, 2),-- tổng phí phạt trả trễ
                             deposit				DECIMAL(10, 2),-- tổng tiền cọc
                             refund_amount		    DECIMAL(10, 2),-- số tiền hoàn
                             rental_fee			    DECIMAL(10, 2),-- phí thuê tính đến thời điểm hiện tại
                             updated_date 		    date,
                             deleted_date 		    date
);



drop table if exists status_order;
CREATE TABLE status_order(
                             id 				integer	PRIMARY KEY,
                             name_status		text,
                             description		text,
                             created_date 	    date default now(),
                             updated_date 	    date,
                             deleted_date 	    date
);