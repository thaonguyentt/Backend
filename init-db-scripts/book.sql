-- Genre
drop table if exists GENRE;
create table GENRE
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    name         VARCHAR               not null,
    name_vn      text                  not null,
    created_date date default now(),
    updated_date date,
    deleted_date date
);
insert into GENRE (id, name, name_vn, created_date)
values (1, 'Academic', 'Học thuật', '2024-03-01'),
       (2, 'Autobiography', 'Hồi ký', '2024-03-01'),
       (3, 'Classics', 'Kinh điển', '2024-03-01'),
       (4, 'Comedy', 'Hài kịch', '2024-03-01'),
       (5, 'Comics', 'truyện tranh', '2024-03-01'),
       (6, 'Culture', 'Văn hoá', '2024-03-01'),
       (7, 'Drama', 'Kịch', '2024-03-01'),
       (8, 'Economics', 'Kinh tế học', '2024-03-01'),
       (9, 'Education', 'Giáo dục', '2024-03-01'),
       (10, 'Environment', 'Môi trường', '2024-03-01'),
       (11, 'Epic', 'Sử thi', '2024-03-01'),
       (12, 'Fairy Tales', 'Truyện cổ tích', '2024-03-01'),
       (13, 'Fantasy', 'Tưởng tượng', '2024-03-01'),
       (14, 'Fiction', 'Viễn tưởng', '2024-03-01'),
       (15, 'Geography', 'Địa lý', '2024-03-01'),
       (16, 'Ghosts', 'ma', '2024-03-01'),
       (17, 'Health', 'Sức khỏe', '2024-03-01'),
       (18, 'History', 'Lịch sử', '2024-03-01'),
       (19, 'Horror', 'Kinh dị', '2024-03-01'),
       (20, 'Humanities', 'Nhân văn', '2024-03-01'),
       (21, 'Literature', 'Văn học', '2024-03-01'),
       (22, 'Mystery', 'Bí ẩn', '2024-03-01'),
       (23, 'Mythology', 'Thần thoại', '2024-03-01'),
       (24, 'name', 'tên', '2024-03-01'),
       (25, 'Nature', 'Thiên nhiên', '2024-03-01'),
       (26, 'Novels', 'Tiểu thuyết', '2024-03-01'),
       (27, 'Physics', 'Vật lý', '2024-03-01'),
       (28, 'Poetry', 'Thơ', '2024-03-01'),
       (29, 'Psychology', 'Tâm lý', '2024-03-01'),
       (30, 'Religion', 'Tôn giáo', '2024-03-01'),
       (31, 'Romance', 'Lãng mạn', '2024-03-01'),
       (32, 'Romantic', 'Lãng mạn', '2024-03-01'),
       (33, 'Science', 'Khoa học', '2024-03-01'),
       (34, 'Short Stories', 'Truyện ngắn', '2024-03-01'),
       (35, 'Society', 'Xã hội', '2024-03-01'),
       (36, 'Sociology', 'Xã hội học', '2024-03-01'),
       (37, 'Sports', 'Các môn thể thao', '2024-03-01'),
       (38, 'Suspense', 'Hồi hộp', '2024-03-01'),
       (39, 'Textbooks', 'Sách giáo khoa', '2024-03-01'),
       (40, 'Thriller', 'Giật gân', '2024-03-01');


-- Book
drop table if exists BOOK;
create table BOOK
(
    id             BIGSERIAL primary key,
    isbn           VARCHAR UNIQUE NOT NULL,
    title          VARCHAR        NOT NULL,
    authors        text[], -- new table on its own ?
    language       VARCHAR references common_language (code),
    genre          text[],
    publisher      VARCHAR,
    published_date date,
    page_count     int,
    size           text,
    created_date   date default now(),
    updated_date   date,
    deleted_date   date
);

-- Copies
drop table if exists COPY;
create table COPY
(
    id             BIGSERIAL PRIMARY KEY,
    book_id        BIGSERIAL REFERENCES BOOK (id) NOT NULL,
    owner_id       BIGSERIAL,
    image_link     text,
    price          DECIMAL(10, 2),
    damage_percent NUMERIC,
    deposit        DECIMAL(10, 2),
    penalty_fee    DECIMAL(10, 2),
    created_date   date default now(),
    updated_date   date,
    deleted_date   date
);

-- Listing
drop table if exists LISTING;
create table LISTING
(
    id           BIGSERIAL primary key,
    copy_id      bigserial references COPY (id),
    expiry_date  date,
    description  text,
    created_date date default now(),
    updated_date date,
    deleted_date date
);