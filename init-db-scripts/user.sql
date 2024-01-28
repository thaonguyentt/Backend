drop table if exists USER_USER;
CREATE TABLE USER_USER
(
    id           BIGSERIAL primary key ,
    username     VARCHAR UNIQUE NOT NULL,
    email        VARCHAR UNIQUE NOT NULL,
    phone_number VARCHAR UNIQUE,
    password     VARCHAR,
    first_name   VARCHAR not null,
    last_name    varchar not null,
    birthdate    date,
    created_date date default now(),
    updated_date date,
    deleted_date date
);
insert into user_user (username, email, first_name, last_name)
values ('dev', 'dev@app.local', 'Dev', 'Developer');


drop table if exists user_password;
CREATE TABLE user_password(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    password TEXT NOT NULL,
    created_date date default now(),
    updated_date date,
    deleted_date date
);
