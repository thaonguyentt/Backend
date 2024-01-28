
drop table if exists common_area_types;
create table common_area_types(
    id  int primary key ,
    name varchar not null,
    description varchar
);
insert into common_area_types (id, name, description)
values (0, 'Khác', 'Đơn vị hành chính đặc biệt'),
       (1, 'Tỉnh/Thành phố trực thuộc Trung ương', null),
       (2, 'Huyện/Quận/Thị xã/Thành phố thuộc tỉnh', null),
       (3, 'Xã/Phường/Thị trấn', null)
;

drop table if exists common_locale_classification;
create table common_locale_classification (
    id int primary key ,
    name varchar not null ,
    description varchar not null
);
insert into common_locale_classification (id, name, description)
values
    (1, 'Vùng sâu, vùng xa', 'Khu vực 1 (KV1) gồm các địa phương thuộc miền núi, vùng cao, vùng sâu, hải đảo, trong đó có các xã thuộc vùng có điều kiện kinh tế - xã hội đặc biệt khó khăn theo quy định của Chính phủ.'),
    (2, 'Nông thôn', 'Khu vực 2 - nông thôn (KV2-NT): Các địa phương không thuộc KV1, KV2, KV3.'),
    (3, 'Ngoại ô', 'Khu vực 2 (KV2): Còn lại là các thành phố trực thuộc tỉnh (không trực thuộc trung ương); các thị xã; các huyện ngoại thành của thành phố trực thuộc trung ương.'),
    (4, 'Thành thị', 'Khu vực 3 (KV3) gồm: Các quận nội thành của thành phố trực thuộc trung ương.')
;

drop table if exists common_sex;
create table common_sex (
    id int primary key not null ,
    description varchar not null
);
insert into common_sex
values (0, 'Not known'),
       (1, 'Male'),
       (2, 'Female'),
       (9, 'Not applicable')
;


