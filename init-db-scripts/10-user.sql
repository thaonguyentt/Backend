
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
INSERT INTO user_user (id,username,email,phone_number,first_name,last_name,birth_date,avatar_url,address)
VALUES
    (1,'dev','dev@app.local','334543678','Dev','Developer','2017-03-14','https://loremflickr.com/640/480/cats?lock=72704','Đường Hoàng Quốc Việt, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội'),
    (2,'dev0','Tracy47@yahoo.com','334543678','Tracy','Braun','2017-03-14','https://loremflickr.com/640/480/cats?lock=72704','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội'),
    (3,'dev1','Nigel11@gmail.com','334543678','Nigel','Romaguera','2017-03-14','https://loremflickr.com/640/480/cats?lock=51706','phố minh khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội'),
    (4,'dev2','Giovani_Kertzmann1@yahoo.com','334543678','Giovani','Kertzmann','2017-03-14','https://loremflickr.com/640/480/cats?lock=61548','Đường Võng Thị, Phường Thụy Khuê, Quận Tây Hồ, Hà Nội'),
    (5,'dev3','Charlotte_Blick64@gmail.com','334543678','Charlotte','Blick','2017-03-14','https://loremflickr.com/640/480/cats?lock=67541','Đường Kim Giang, Phường Kim Giang, Quận Thanh Xuân, Hà Nội'),
    (6,'dev4','Adonis_Labadie65@gmail.com','334543678','Adonis','Labadie','2017-03-14','https://loremflickr.com/640/480/cats?lock=23601','Đường Yên Hòa, Phường Yên Hoà, Quận Cầu Giấy, Hà Nội'),
    (7,'dev5','Toby.Emmerich@hotmail.com','334543678','Toby','Emmerich','2017-03-14','https://loremflickr.com/640/480/cats?lock=4462','Đường Tây Sơn, Phường Trung Liệt, Quận Đống Đa, Hà Nội'),
    (8,'dev6','Adolph_Runolfsson@hotmail.com','334543678','Adolph','Runolfsson','2017-03-14','https://loremflickr.com/640/480/cats?lock=72736','Đường Lò Đúc, Phường Đống Mác, Quận Hai Bà Trưng, Hà Nội'),
    (9,'dev7','Leda_Bauch43@yahoo.com','334543678','Leda','Bauch','2017-03-14','https://loremflickr.com/640/480/cats?lock=73742','Đường Xuân La, Phường Xuân La, Quận Tây Hồ, Hà Nội'),
    (10,'dev8','Lilian.Weber10@gmail.com','334543678','Lilian','Weber','2017-03-14','https://loremflickr.com/640/480/cats?lock=39704','Đường 19/5, Phường Văn Quán, Quận Hà Đông, Hà Nội'),
    (11,'dev9','Loyce39@yahoo.com','334543678','Loyce','Wiza','2017-03-14','https://loremflickr.com/640/480/cats?lock=9074','Đường Tựu Liệt, Thị trấn Văn Điển, Huyện Thanh Trì, Hà Nội'),
    (12,'dev10','Edmond.OConner@yahoo.com','334543678','Edmond','O''Conner','2017-03-14','https://loremflickr.com/640/480/cats?lock=74665','Đường Định Công Hạ, Phường Định Công, Quận Hoàng Mai, Hà Nội'),
    (13,'dev11','Mekhi_Becker30@yahoo.com','334543678','Mekhi','Becker','2017-03-14','https://loremflickr.com/640/480/cats?lock=46429','Đường Bồ Đề, Phường Bồ Đề, Quận Long Biên, Hà Nội'),
    (14,'dev12','Annamarie29@yahoo.com','334543678','Annamarie','Mante','2017-03-14','https://loremflickr.com/640/480/cats?lock=80942','Đường Quang Trung, Phường Quang Trung, Quận Hà Đông, Hà Nội'),
    (15,'dev13','Catherine51@hotmail.com','334543678','Catherine','Goodwin','2017-03-14','https://loremflickr.com/640/480/cats?lock=47009','Đường Đê Trần Khát Chân, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội'),
    (16,'dev14','Elmira.Beier@gmail.com','334543678','Elmira','Beier','2017-03-14','https://loremflickr.com/640/480/cats?lock=27353','Đường Bồ Đề, Phường Bồ Đề, Quận Long Biên, Hà Nội'),
    (17,'dev15','Maegan_Osinski@yahoo.com','334543678','Maegan','Osinski','2017-03-14','https://loremflickr.com/640/480/cats?lock=68941','Đường Khương Trung, Phường Khương Trung, Quận Thanh Xuân, Hà Nội'),
    (18,'dev16','Connie_Runolfsson@yahoo.com','334543678','Connie','Runolfsson','2017-03-14','https://loremflickr.com/640/480/cats?lock=25770','Đường Phúc Tân, Phường Phúc Tân, Quận Hoàn Kiếm, Hà Nội'),
    (19,'dev17','Kathryne_Witting@yahoo.com','334543678','Kathryne','Witting','2017-03-14','https://loremflickr.com/640/480/cats?lock=99461','Đường Nguyễn Văn Cừ, Phường Gia Thụy, Quận Long Biên, Hà Nội'),
    (20,'dev18','Adolfo_Mann@yahoo.com','334543678','Adolfo','Mann','2017-03-14','https://loremflickr.com/640/480/cats?lock=5993','Đường Khương Đình, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (21,'dev19','Francis.Johnston16@hotmail.com','334543678','Francis','Johnston','2017-03-14','https://loremflickr.com/640/480/cats?lock=73436','Đường Phương Canh, Phường Phương Canh, Quận Nam Từ Liêm, Hà Nội'),
    (22,'dev20','Schuyler_Harvey@gmail.com','334543678','Schuyler','Harvey','2017-03-14','https://loremflickr.com/640/480/cats?lock=74918','Đường Trần Phú, Phường Văn Quán, Quận Hà Đông, Hà Nội'),
    (23,'dev21','Jon.Ebert30@hotmail.com','334543678','Jon','Ebert','2017-03-14','https://loremflickr.com/640/480/cats?lock=75208','Đường Trương Định, Phường Tương Mai, Quận Hoàng Mai, Hà Nội'),
    (24,'dev22','Mark27@gmail.com','334543678','Mark','Vandervort','2017-03-14','https://loremflickr.com/640/480/cats?lock=84467','Đường Xuân La, Phường Xuân La, Quận Tây Hồ, Hà Nội'),
    (25,'dev23','Zane.Schiller74@yahoo.com','334543678','Zane','Schiller','2017-03-14','https://loremflickr.com/640/480/cats?lock=55218','Đường Tố Hữu, Phường La Khê, Quận Hà Đông, Hà Nội'),
    (26,'dev24','Margaretta49@hotmail.com','334543678','Margaretta','Schaden','2017-03-14','https://loremflickr.com/640/480/cats?lock=5632','Đường Đồng Me, Phường Mễ Trì, Quận Nam Từ Liêm, Hà Nội'),
    (27,'dev25','Leopoldo16@gmail.com','334543678','Leopoldo','Boehm','2017-03-14','https://loremflickr.com/640/480/cats?lock=20211','Đường Lê Trọng Tấn, Phường Khương Mai, Quận Thanh Xuân, Hà Nội'),
    (28,'dev26','Joe76@gmail.com','334543678','Joe','Rogahn','2017-03-14','https://loremflickr.com/640/480/cats?lock=34387','Đường Láng Hạ, Phường Láng Hạ, Quận Đống Đa, Hà Nội'),
    (29,'dev27','Isidro_Renner54@gmail.com','334543678','Isidro','Renner','2017-03-14','https://loremflickr.com/640/480/cats?lock=98813','Đường Nguyễn Khánh Toàn, Phường Quan Hoa, Quận Cầu Giấy, Hà Nội'),
    (30,'dev28','Daryl95@yahoo.com','334543678','Daryl','Cronin','2017-03-14','https://loremflickr.com/640/480/cats?lock=22864','180/61/5, Đường Tây Mỗ, Phường Tây Mỗ, Quận Nam Từ Liêm, Hà Nội'),
    (31,'dev29','Eloise.Terry@gmail.com','334543678','Eloise','Terry','2017-03-14','https://loremflickr.com/640/480/cats?lock=98186','Đường Núi Trúc, Phường Ngọc Khánh, Quận Ba Đình, Hà Nội'),
    (32,'dev30','Jake82@hotmail.com','334543678','Jake','Schoen','2017-03-14','https://loremflickr.com/640/480/cats?lock=3409','Đường Tô Hiệu, Phường Quang Trung, Quận Hà Đông, Hà Nội'),
    (33,'dev31','Christophe.Mohr7@gmail.com','334543678','Christophe','Mohr','2017-03-14','https://loremflickr.com/640/480/cats?lock=62011','Đường Ngọc Trục, Phường Đại Mỗ, Quận Nam Từ Liêm, Hà Nội'),
    (34,'dev32','Shanny_Leffler49@gmail.com','334543678','Shanny','Leffler','2017-03-14','https://loremflickr.com/640/480/cats?lock=1778','Đường Lạc Long Quân, Phường Nghĩa Đô, Quận Cầu Giấy, Hà Nội'),
    (35,'dev33','Kaitlin_Dicki@hotmail.com','334543678','Kaitlin','Dicki','2017-03-14','https://loremflickr.com/640/480/cats?lock=48251','Đường Tả Thanh Oai, Xã Tả Thanh Oai, Huyện Thanh Trì, Hà Nội'),
    (36,'dev34','Marion_Braun35@yahoo.com','334543678','Marion','Braun','2017-03-14','https://loremflickr.com/640/480/cats?lock=79933','Đường Mỹ Đình, Phường Mỹ Đình 1, Quận Nam Từ Liêm, Hà Nội'),
    (37,'dev35','Jefferey11@hotmail.com','334543678','Jefferey','Kertzmann','2017-03-14','https://loremflickr.com/640/480/cats?lock=15991','Đường Nguyễn Chí Thanh, Phường Láng Hạ, Quận Đống Đa, Hà Nội'),
    (38,'dev36','Hilton_Langosh5@yahoo.com','334543678','Hilton','Langosh','2017-03-14','https://loremflickr.com/640/480/cats?lock=30601','Đường Vĩnh Quỳnh, Xã Tam Hiệp, Huyện Thanh Trì, Hà Nội'),
    (39,'dev37','Dejah_Kihn5@hotmail.com','334543678','Dejah','Kihn','2017-03-14','https://loremflickr.com/640/480/cats?lock=62618','Đội Cấn, Phường Cống Vị, Quận Ba Đình, Hà Nội'),
    (40,'dev38','Kyler_Miller@hotmail.com','334543678','Kyler','Miller','2017-03-14','https://loremflickr.com/640/480/cats?lock=37851','Đường Phố Vọng, Phường Bách Khoa, Quận Hai Bà Trưng, Hà Nội'),
    (41,'dev39','David.Hills@hotmail.com','334543678','David','Hills','2017-03-14','https://loremflickr.com/640/480/cats?lock=20731','Đường Vĩnh Phúc, Phường Vĩnh Phúc, Quận Ba Đình, Hà Nội'),
    (42,'dev40','Omer.Harvey@gmail.com','334543678','Omer','Harvey','2017-03-14','https://loremflickr.com/640/480/cats?lock=90234','Đường Khương Trung, Phường Khương Trung, Quận Thanh Xuân, Hà Nội'),
    (43,'dev41','Destiny19@yahoo.com','334543678','Destiny','Baumbach','2017-03-14','https://loremflickr.com/640/480/cats?lock=5243','Ngách 6A, Đường Tả Thanh Oai, Xã Tả Thanh Oai, Huyện Thanh Trì, Hà Nội'),
    (44,'dev42','Marilie30@gmail.com','334543678','Marilie','Pagac','2017-03-14','https://loremflickr.com/640/480/cats?lock=64244','Đường Yên Vĩnh, Xã Kim Chung, Huyện Hoài Đức, Hà Nội'),
    (45,'dev43','Brenna_Skiles70@hotmail.com','334543678','Brenna','Skiles','2017-03-14','https://loremflickr.com/640/480/cats?lock=31915','4, Đường Kim Giang, Phường Đại Kim, Quận Hoàng Mai, Hà Nội'),
    (46,'dev44','Marilyne_Leannon92@hotmail.com','334543678','Marilyne','Leannon','2017-03-14','https://loremflickr.com/640/480/cats?lock=75049','Bùi Xương Trạch, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (47,'dev45','Jamaal0@gmail.com','334543678','Jamaal','Bartoletti','2017-03-14','https://loremflickr.com/640/480/cats?lock=82696','Đường Lĩnh Nam, Phường Mai Động, Quận Hoàng Mai, Hà Nội'),
    (48,'dev46','Odessa_Towne@hotmail.com','334543678','Odessa','Towne','2017-03-14','https://loremflickr.com/640/480/cats?lock=47465','Đường Tôn Thất Tùng, Phường Trung Tự, Quận Đống Đa, Hà Nội'),
    (49,'dev47','Lora.Yost2@gmail.com','334543678','Lora','Yost','2017-03-14','https://loremflickr.com/640/480/cats?lock=41943','văn phu, Phường Kiến Hưng, Quận Hà Đông, Hà Nội'),
    (50,'dev48','Domenick.Boyle@yahoo.com','334543678','Domenick','Boyle','2017-03-14','https://loremflickr.com/640/480/cats?lock=71328','Đường Núi Trúc, Phường Trúc Bạch, Quận Ba Đình, Hà Nội'),
    (51,'dev49','Larissa.Dibbert@gmail.com','334543678','Larissa','Dibbert','2017-03-14','https://loremflickr.com/640/480/cats?lock=45796','88, Đường Phạm Văn Đồng, Phường Cổ Nhuế 1, Quận Bắc Từ Liêm, Hà Nội'),
    (52,'dev50','Filomena67@hotmail.com','334543678','Filomena','Deckow','2017-03-14','https://loremflickr.com/640/480/cats?lock=61276','Đường Đào Tấn, Phường Cống Vị, Quận Ba Đình, Hà Nội'),
    (53,'dev51','Cruz.Auer35@gmail.com','334543678','Cruz','Auer','2017-03-14','https://loremflickr.com/640/480/cats?lock=66437','đức giang, Phường Đức Giang, Quận Long Biên, Hà Nội'),
    (54,'dev52','Alejandra.Stanton@gmail.com','334543678','Alejandra','Stanton','2017-03-14','https://loremflickr.com/640/480/cats?lock=32035','Đường Nguyễn Cơ Thạch, Phường Mỹ Đình 2, Quận Nam Từ Liêm, Hà Nội'),
    (55,'dev53','Damian_Brakus83@gmail.com','334543678','Damian','Brakus','2017-03-14','https://loremflickr.com/640/480/cats?lock=68652','Đường Lạc Long Quân, Phường Bưởi, Quận Tây Hồ, Hà Nội'),
    (56,'dev54','Marlon.McGlynn@gmail.com','334543678','Marlon','McGlynn','2017-03-14','https://loremflickr.com/640/480/cats?lock=92985','Đường Minh Khai, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội'),
    (57,'dev55','Myrl47@gmail.com','334543678','Myrl','Bogan','2017-03-14','https://loremflickr.com/640/480/cats?lock=74687','Đường Chùa Láng, Phường Láng Hạ, Quận Đống Đa, Hà Nội'),
    (58,'dev56','Keara16@hotmail.com','334543678','Keara','Rogahn','2017-03-14','https://loremflickr.com/640/480/cats?lock=42869','Khương Hạ, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (59,'dev57','Francisco55@yahoo.com','334543678','Francisco','Jast','2017-03-14','https://loremflickr.com/640/480/cats?lock=53009','tả thanh oai, Xã Tả Thanh Oai, Huyện Thanh Trì, Hà Nội'),
    (60,'dev58','Alana.Shanahan@hotmail.com','334543678','Alana','Shanahan','2017-03-14','https://loremflickr.com/640/480/cats?lock=98805','Đường Xã Đàn 2, Phường Ô Chợ Dừa, Quận Đống Đa, Hà Nội'),
    (61,'dev59','Melody.Flatley66@yahoo.com','334543678','Melody','Flatley','2017-03-14','https://loremflickr.com/640/480/cats?lock=96424','Đường Bùi Xương Trạch, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (62,'dev60','Roger.Beier@hotmail.com','334543678','Roger','Beier','2017-03-14','https://loremflickr.com/640/480/cats?lock=64160','Đường Tả Thanh Oai, Xã Tả Thanh Oai, Huyện Thanh Trì, Hà Nội'),
    (63,'dev61','Chaim_Sipes@hotmail.com','334543678','Chaim','Sipes','2017-03-14','https://loremflickr.com/640/480/cats?lock=31183','Đường Phố Trạm, Phường Long Biên, Quận Long Biên, Hà Nội'),
    (64,'dev62','Zaria_Grady36@hotmail.com','334543678','Zaria','Grady','2017-03-14','https://loremflickr.com/640/480/cats?lock=4164','Đường Đền Lừ, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (65,'dev63','Dayna28@gmail.com','334543678','Dayna','Bartoletti','2017-03-14','https://loremflickr.com/640/480/cats?lock=45691','Đường Doãn Kế Thiện, Phường Mai Dịch, Quận Cầu Giấy, Hà Nội'),
    (66,'dev64','Devonte51@hotmail.com','334543678','Devonte','Heller','2017-03-14','https://loremflickr.com/640/480/cats?lock=61446','Đường Hoàng Mai, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (67,'dev65','Jayme27@hotmail.com','334543678','Jayme','Ledner','2017-03-14','https://loremflickr.com/640/480/cats?lock=86008','Đường Quan Nhân, Phường Nhân Chính, Quận Thanh Xuân, Hà Nội'),
    (68,'dev66','Wilfrid86@yahoo.com','334543678','Wilfrid','Welch','2017-03-14','https://loremflickr.com/640/480/cats?lock=88407','Đường Gốc Đề, Phường Minh Khai, Quận Hai Bà Trưng, Hà Nội'),
    (69,'dev67','Juvenal.Daugherty@hotmail.com','334543678','Juvenal','Daugherty','2017-03-14','https://loremflickr.com/640/480/cats?lock=51585','Đường Vạn Phúc, Phường Vạn Phúc, Quận Hà Đông, Hà Nội'),
    (70,'dev68','Rosa.Ruecker6@yahoo.com','334543678','Rosa','Ruecker','2017-03-14','https://loremflickr.com/640/480/cats?lock=57982','Đường Láng Hạ, Phường Láng Hạ, Quận Đống Đa, Hà Nội'),
    (71,'dev69','Vicky25@yahoo.com','334543678','Vicky','Corwin','2017-03-14','https://loremflickr.com/640/480/cats?lock=92555','Đường Đê Trần Khát Chân, Phường Thanh Lương, Quận Hai Bà Trưng, Hà Nội'),
    (72,'dev70','Hazle86@hotmail.com','334543678','Hazle','Hudson','2017-03-14','https://loremflickr.com/640/480/cats?lock=36228','Đường Ngọc Hà, Phường Ngọc Hà, Quận Ba Đình, Hà Nội'),
    (73,'dev71','Corene60@gmail.com','334543678','Corene','Cummings','2017-03-14','https://loremflickr.com/640/480/cats?lock=81638','Đường Hào Nam, Phường Ô Chợ Dừa, Quận Đống Đa, Hà Nội'),
    (74,'dev72','Isac.Bergnaum@gmail.com','334543678','Isac','Bergnaum','2017-03-14','https://loremflickr.com/640/480/cats?lock=30743','Lĩnh nam, Phường Lĩnh Nam, Quận Hoàng Mai, Hà Nội'),
    (75,'dev73','Earnest_Schuster@hotmail.com','334543678','Earnest','Schuster','2017-03-14','https://loremflickr.com/640/480/cats?lock=11210','Đường Bùi Xương Trạch, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (76,'dev74','Willie_Zboncak@hotmail.com','334543678','Willie','Zboncak','2017-03-14','https://loremflickr.com/640/480/cats?lock=91784','Đường Khương Hạ, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (77,'dev75','Blanche.Kohler48@hotmail.com','334543678','Blanche','Kohler','2017-03-14','https://loremflickr.com/640/480/cats?lock=36897','Đường Hoàng Mai, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (78,'dev76','Rosalee_Zulauf@gmail.com','334543678','Rosalee','Zulauf','2017-03-14','https://loremflickr.com/640/480/cats?lock=88046','Xuân Đinh, Phường Xuân Đỉnh, Quận Bắc Từ Liêm, Hà Nội'),
    (79,'dev77','Nina92@yahoo.com','334543678','Nina','Osinski','2017-03-14','https://loremflickr.com/640/480/cats?lock=56184','Đường Hoàng Mai, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (80,'dev78','Izabella.McCullough44@yahoo.com','334543678','Izabella','McCullough','2017-03-14','https://loremflickr.com/640/480/cats?lock=81963','Đường Phú Đô, Phường Phú Đô, Quận Nam Từ Liêm, Hà Nội'),
    (81,'dev79','Flo43@yahoo.com','334543678','Flo','Auer','2017-03-14','https://loremflickr.com/640/480/cats?lock=86340','Đường Văn Cao, Phường Liễu Giai, Quận Ba Đình, Hà Nội'),
    (82,'dev80','Tatum95@yahoo.com','334543678','Tatum','Hartmann','2017-03-14','https://loremflickr.com/640/480/cats?lock=24973','Đường Phố Trạm, Phường Long Biên, Quận Long Biên, Hà Nội'),
    (83,'dev81','Vesta.Rolfson@gmail.com','334543678','Vesta','Rolfson','2017-03-14','https://loremflickr.com/640/480/cats?lock=12132','1, Đường Nguyễn Khánh Toàn, Phường Quan Hoa, Quận Cầu Giấy, Hà Nội'),
    (84,'dev82','Kavon88@hotmail.com','334543678','Kavon','Carter','2017-03-14','https://loremflickr.com/640/480/cats?lock=89981','Nguyễn Thái Học, Thị trấn Phùng, Huyện Đan Phượng, Hà Nội'),
    (85,'dev83','Giovanna.Halvorson46@hotmail.com','334543678','Giovanna','Halvorson','2017-03-14','https://loremflickr.com/640/480/cats?lock=59365','Đường Hoàng Mai, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (86,'dev84','Estell.Dach@hotmail.com','334543678','Estell','Dach','2017-03-14','https://loremflickr.com/640/480/cats?lock=21791','Đường Đê La Thành, Phường Kim Liên, Quận Đống Đa, Hà Nội'),
    (87,'dev85','Wilfredo.Walter39@hotmail.com','334543678','Wilfredo','Walter','2017-03-14','https://loremflickr.com/640/480/cats?lock=15342','Đường Quang Trung, Phường Quang Trung, Quận Hà Đông, Hà Nội'),
    (88,'dev86','Chester.Cronin79@hotmail.com','334543678','Chester','Cronin','2017-03-14','https://loremflickr.com/640/480/cats?lock=8778','Đường Đức Diễn, Phường Phúc Diễn, Quận Bắc Từ Liêm, Hà Nội'),
    (89,'dev87','Ubaldo.Kozey@yahoo.com','334543678','Ubaldo','Kozey','2017-03-14','https://loremflickr.com/640/480/cats?lock=11039','Đường Kim Mã, Phường Kim Mã, Quận Ba Đình, Hà Nội'),
    (90,'dev88','Dustin8@hotmail.com','334543678','Dustin','Schoen','2017-03-14','https://loremflickr.com/640/480/cats?lock=77492','Đường Trung Phụng, Phường Trung Phụng, Quận Đống Đa, Hà Nội'),
    (91,'dev89','Garfield_Hirthe52@hotmail.com','334543678','Garfield','Hirthe','2017-03-14','https://loremflickr.com/640/480/cats?lock=81392','Đường Lĩnh Nam, Phường Lĩnh Nam, Quận Hoàng Mai, Hà Nội'),
    (92,'dev90','Ryder.McDermott45@gmail.com','334543678','Ryder','McDermott','2017-03-14','https://loremflickr.com/640/480/cats?lock=70929','Đường Đền Lừ 1, Phường Tân Mai, Quận Hoàng Mai, Hà Nội'),
    (93,'dev91','Lelah_Crooks@yahoo.com','334543678','Lelah','Crooks','2017-03-14','https://loremflickr.com/640/480/cats?lock=69426','Đường Quan Nhân, Phường Nhân Chính, Quận Thanh Xuân, Hà Nội'),
    (94,'dev92','Terry88@gmail.com','334543678','Terry','Metz','2017-03-14','https://loremflickr.com/640/480/cats?lock=25853','Đường Hàm Nghi, Phường Cầu Diễn, Quận Nam Từ Liêm, Hà Nội'),
    (95,'dev93','Liza19@gmail.com','334543678','Liza','Schoen','2017-03-14','https://loremflickr.com/640/480/cats?lock=65789','Đường Khương Đình, Phường Khương Đình, Quận Thanh Xuân, Hà Nội'),
    (96,'dev94','Ernestine7@yahoo.com','334543678','Ernestine','Sauer','2017-03-14','https://loremflickr.com/640/480/cats?lock=7473','Đường Hoàng Mai, Phường Hoàng Văn Thụ, Quận Hoàng Mai, Hà Nội'),
    (97,'dev95','Crystel.Thompson75@yahoo.com','334543678','Crystel','Thompson','2017-03-14','https://loremflickr.com/640/480/cats?lock=58834','Văn Phú, Phường La Khê, Quận Hà Đông, Hà Nội'),
    (98,'dev96','Marcella74@gmail.com','334543678','Marcella','Rau','2017-03-14','https://loremflickr.com/640/480/cats?lock=94089','Tây Sơn, Phường Ngã Tư Sở, Quận Đống Đa, Hà Nội'),
    (99,'dev97','Dahlia5@hotmail.com','334543678','Dahlia','McDermott','2017-03-14','https://loremflickr.com/640/480/cats?lock=15796','Đường Phùng Khoang, Phường Trung Văn, Quận Nam Từ Liêm, Hà Nội'),
    (100,'dev98','Christ_Jacobson36@yahoo.com','334543678','Christ','Jacobson','2017-03-14','https://loremflickr.com/640/480/cats?lock=20383','Đường Nguyễn Trãi, Phường Thượng Đình, Quận Thanh Xuân, Hà Nội'),
    (101,'dev99','Kristoffer_Braun49@hotmail.com','334543678','Kristoffer','Braun','2017-03-14','https://loremflickr.com/640/480/cats?lock=95137','Đường Tây Sơn, Phường Ngã Tư Sở, Quận Đống Đa, Hà Nội')
;

alter sequence user_user_id_seq restart with 1000;


drop table if exists user_password;
CREATE TABLE user_password(
                              id BIGSERIAL PRIMARY KEY ,
                              user_id BIGINT NOT NULL,
                              password TEXT NOT NULL,
                              created_date date default now(),
                              updated_date date,
                              deleted_date date
);
INSERT INTO user_password (id,user_id,password)
VALUES
    (1,1,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (2,2,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (3,3,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (4,4,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (5,5,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (6,6,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (7,7,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (8,8,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (9,9,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (10,10,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (11,11,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (12,12,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (13,13,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (14,14,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (15,15,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (16,16,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (17,17,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (18,18,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (19,19,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (20,20,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (21,21,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (22,22,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (23,23,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (24,24,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (25,25,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (26,26,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (27,27,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (28,28,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (29,29,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (30,30,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (31,31,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (32,32,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (33,33,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (34,34,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (35,35,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (36,36,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (37,37,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (38,38,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (39,39,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (40,40,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (41,41,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (42,42,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (43,43,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (44,44,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (45,45,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (46,46,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (47,47,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (48,48,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (49,49,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (50,50,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (51,51,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (52,52,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (53,53,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (54,54,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (55,55,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (56,56,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (57,57,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (58,58,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (59,59,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (60,60,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (61,61,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (62,62,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (63,63,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (64,64,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (65,65,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (66,66,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (67,67,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (68,68,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (69,69,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (70,70,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (71,71,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (72,72,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (73,73,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (74,74,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (75,75,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (76,76,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (77,77,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (78,78,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (79,79,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (80,80,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (81,81,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (82,82,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (83,83,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (84,84,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (85,85,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (86,86,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (87,87,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (88,88,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (89,89,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (90,90,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (91,91,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (92,92,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (93,93,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (94,94,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (95,95,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (96,96,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (97,97,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (98,98,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (99,99,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (100,100,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6'),
    (101,101,'$2a$10$oO1SJWlHYNK4KTIZvxDuXu2iCaDLuKiEMLNzNK8Yv5bJVsemCT7j6');

alter sequence user_password_id_seq restart with 1000;



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





