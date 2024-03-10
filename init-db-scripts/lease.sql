-- Lease
drop table if exists LEASE;
create table LEASE (
    id bigserial primary key ,
    lessor_id bigserial not null,
    lessee_id bigserial not null,
    lessor_address text, -- TODO ??
    lessee_address text, -- TODO ??
    refund_amount DECIMAL(10,2),
    penalty_fee DECIMAL(10,2),
    total_deposit DECIMAL(10,2)
);

-- Lease_detail
drop table if exists LEASE_DETAIL;
create table LEASE_DETAIL(
    id bigserial primary key ,
    lease_id bigserial references LEASE(id) NOT NULL,
    copy_id bigserial not null,
    due_date date not null, -- TODO let it live here or in LEASE table ??
    receive_date date,
    return_date date,
    deposit_amount decimal(10,2),
    penalty_daily_rate decimal(10,2), -- TODO phí phạt mỗi ngày
    penalty_amount decimal(10,2), -- TODO dùng cái trên thì cái này ko cần
    refund_amount decimal(10,2),
    rent_rate decimal(10,2), -- TODO phí thuê mỗi ngày => tính ra nhân lên
    created_date date default now(),
    updated_date date,
    deleted_date date
);

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