drop table if exists files;
create table files (
    id uuid primary key ,
    owner_id int ,
    original_name varchar(255) not null
)