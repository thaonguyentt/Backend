drop table if exists VOUCHER_SESSION;
CREATE TABLE VOUCHER_SESSION (
                                 id 				BIGSERIAL 		PRIMARY KEY NOT NULL,              				-- Unique identifier for each voucher
                                 name 			VARCHAR(255) 	NOT NULL,                     			-- Voucher name
                                 code VARCHAR(50) NOT NULL, -- Voucher code

                                 created_date 	DATE 							default now(),
                                 start_date 		DATE 			NOT null 		default now(),          -- Start date of voucher validity
                                 end_date 		DATE 			NOT NULL,                         		-- End date of voucher validity
                                 min_value 		DECIMAL(15, 2) 	DEFAULT 0,             					-- Minimum order value to apply the voucher

    -- Discount amount or percentage
                                 discount_amount DECIMAL(15, 2) 	DEFAULT NULL,    						-- Discount amount (if it's a fixed discount)
                                 discount_percentage DECIMAL(5, 2) DEFAULT NULL, 						-- Discount percentage (if it's a percentage discount)

                                 voucher_type INT NOT NULL 												-- Voucher type (0: fix amount 1:percentage)
);

drop table if exists VOUCHER_SHOP;
CREATE TABLE VOUCHER_SHOP (
                              id 				BIGSERIAL 		PRIMARY KEY NOT NULL,              				-- Unique identifier for each voucher
                              name 			VARCHAR(255) 	NOT NULL,                     			-- Voucher name
                              code VARCHAR(50) NOT NULL, -- Voucher code

                              created_date 	DATE 							default now(),
                              start_date 		DATE 			NOT null 		default now(),          -- Start date of voucher validity
                              end_date 		DATE 			NOT NULL,                         		-- End date of voucher validity
                              min_value 		DECIMAL(15, 2) 	DEFAULT 0,             					-- Minimum order value to apply the voucher

    -- Discount amount or percentage
                              discount_amount DECIMAL(15, 2) 	DEFAULT NULL,    						-- Discount amount (if it's a fixed discount)
                              discount_percentage DECIMAL(5, 2) DEFAULT NULL, 						-- Discount percentage (if it's a percentage discount)

                              voucher_type INT NOT NULL 												-- Voucher type (0: fix amount 1:percentage)
);
-- Insert sample data for VOUCHER_SESSION with code field
INSERT INTO VOUCHER_SESSION (name, start_date, end_date, min_value, discount_amount, discount_percentage, voucher_type, code)
VALUES
    ('New Year Discount', '2024-01-01', '2024-01-31', 50.00, 10.00, NULL, 0, 'NY2024'),
    ('Winter Sale', '2024-02-01', '2024-02-28', 100.00, NULL, 15.00, 1, 'WINTER15'),
    ('Summer Special', '2024-06-01', '2024-06-30', 200.00, 25.00, NULL, 0, 'SUMMER25');


-- Insert sample data for VOUCHER_SHOP with code field
INSERT INTO VOUCHER_SHOP (name, start_date, end_date, min_value, discount_amount, discount_percentage, voucher_type, code)
VALUES
    ('Shop Anniversary', '2024-03-01', '2024-03-15', 150.00, 20.00, NULL, 0, 'ANNIV20'),
    ('Flash Sale', '2024-04-01', '2024-04-07', 75.00, NULL, 5.00, 1, 'FLASH5'),
    ('VIP Member Discount', '2024-05-01', '2024-05-31', 300.00, 50.00, NULL, 0, 'VIP50');
