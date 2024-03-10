-- Genre
drop table if exists GENRE;
create table GENRE
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    name         VARCHAR               not null,
    description  text                  not null,
    created_date date default now(),
    updated_date date,
    deleted_date date
);


-- Book
drop table if exists BOOK;
create table BOOK
(
    id             BIGSERIAL primary key,
    isbn           VARCHAR UNIQUE NOT NULL,
    title          VARCHAR        NOT NULL,
    authors        text,                    -- new table on its own ?
    language       VARCHAR        NOT NULL, -- TODO new common table
    genre_id       BIGSERIAL references GENRE (id),
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
create table LISTING (
    id BIGSERIAL primary key ,
    copy_id bigserial references COPY(id),
    expiry_date date,
    description text,
    created_date date default now(),
    updated_date date,
    deleted_date date
);