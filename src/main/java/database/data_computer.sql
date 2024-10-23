
-- select * from product
-- where name like '%Asus%'
-- insert into product(name,)
-- values ()


INSERT INTO suppliers (company_name, phone_number, address, contact_date, email)
VALUES 
('Phong Vũ Computer', '18006865', 'Tầng 2, số 2A Trần Đại Nghĩa, Hai Bà Trưng, Hà Nội', '2024-10-17', 'duynguyen@gamil.com'),
('GearVN', '18006173', '78-80 Hoàng Hoa Thám, P.12, Q.Tân Bình, TP.HCM', '2024-10-17', 'gearvn@gmail.com'),
('Hanoicomputer', '19001903', '131 Lê Thanh Nghị, Hai Bà Trưng, Hà Nội', '2024-10-17', 'hnc@hanoicomputer.com'),
('Ben Computer', '0899179993', '74 Nguyễn Khánh Toàn, Cầu Giấy, Hà Nội', '2024-10-17', 'bencomputer@gmail.com');
select * from suppliers;

-- insert into product(suppliers_id, name,quality,price,genre,brand ,operating_system,cpu,memory,ram, made_in,status)
-- values (1)

INSERT INTO product (suppliers_id, name, quality, price, genre, brand, operating_system, cpu, memory, ram, made_in, status)
VALUES 
(1, 'MacBook Air M1 2020', 123, 18190000, 'Laptop', 'Apple', 'macOS', 'Apple M1', '256GB SSD', '8GB', 'USA', 'In Stock'),
(1, 'MacBook Air M2 2023', 500, 27690000, 'Laptop', 'Apple', 'macOS', 'Apple M2', '512GB SSD', '8GB', 'USA', 'In Stock'),
(1, 'Dell XPS 13', 200, 30000000, 'Ultrabook', 'Dell', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'USA', 'In Stock'),
(1, 'Dell XPS 15', 234, 35000000, 'Ultrabook', 'Dell', 'Windows 11', 'Intel Core i7', '1TB SSD', '16GB', 'USA', 'In Stock'),
(1, 'Surface Laptop 5', 567, 24000000, 'Ultrabook', 'Microsoft', 'Windows 11', 'Intel Core i5', '256GB SSD', '8GB', 'USA', 'In Stock'),
(1, 'Asus ROG Zephyrus G15', 456, 40000000, 'Gaming Laptop', 'Asus', 'Windows 11', 'AMD Ryzen 9', '1TB SSD', '16GB', 'Taiwan', 'In Stock'),
(1, 'Lenovo ThinkPad X1 Carbon Gen 7', 464, 30000000, 'Business Laptop', 'Lenovo', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'China', 'In Stock'),
(1, 'HP Spectre X360', 234, 27000000, 'Ultrabook', 'HP', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'USA', 'In Stock'),
(1, 'LG Gram 17', 412, 35000000, 'Ultrabook', 'LG', 'Windows 11', 'Intel Core i7', '1TB SSD', '16GB', 'South Korea', 'In Stock'),
(1, 'MacBook Pro 16 M2 Pro', 123, 60000000, 'Laptop', 'Apple', 'macOS', 'Apple M2 Pro', '1TB SSD', '16GB', 'USA', 'In Stock');

INSERT INTO product (suppliers_id, name, quality, price, genre, brand, operating_system, cpu, memory, ram, made_in, status)
VALUES 
(2, 'Acer Predator Helios 300', 231, 34000000, 'Gaming Laptop', 'Acer', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'Taiwan', 'In Stock'),
(2, 'Asus ZenBook 14', 435, 24000000, 'Ultrabook', 'Asus', 'Windows 11', 'Intel Core i5', '512GB SSD', '8GB', 'Taiwan', 'In Stock'),
(2, 'HP Omen 15', 333, 37000000, 'Gaming Laptop', 'HP', 'Windows 11', 'Intel Core i7', '1TB SSD', '16GB', 'USA', 'In Stock'),
(2, 'Lenovo Legion 5', 454, 32000000, 'Gaming Laptop', 'Lenovo', 'Windows 11', 'AMD Ryzen 7', '512GB SSD', '16GB', 'China', 'In Stock'),
(2, 'MSI GF65 Thin', 788, 28000000, 'Gaming Laptop', 'MSI', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'Taiwan', 'In Stock'),
(2, 'Dell Inspiron 15 7000', 333, 26000000, 'Laptop', 'Dell', 'Windows 11', 'Intel Core i7', '512GB SSD', '8GB', 'USA', 'In Stock'),
(2, 'Asus VivoBook S15', 22, 21000000, 'Laptop', 'Asus', 'Windows 11', 'Intel Core i5', '256GB SSD', '8GB', 'Taiwan', 'In Stock'),
(2, 'HP Pavilion x360', 12, 23000000, '2-in-1 Laptop', 'HP', 'Windows 11', 'Intel Core i5', '512GB SSD', '8GB', 'USA', 'In Stock'),
(2, 'Acer Swift 3', 23, 19000000, 'Ultrabook', 'Acer', 'Windows 11', 'AMD Ryzen 5', '512GB SSD', '8GB', 'Taiwan', 'In Stock'),
(2, 'Lenovo Yoga Slim 7', 456, 28000000, 'Ultrabook', 'Lenovo', 'Windows 11', 'AMD Ryzen 7', '512GB SSD', '16GB', 'China', 'In Stock');

INSERT INTO product (suppliers_id, name, quality, price, genre, brand, operating_system, cpu, memory, ram, made_in, status)
VALUES 
(3, 'Razer Blade 15', 345, 50000000, 'Gaming Laptop', 'Razer', 'Windows 11', 'Intel Core i9', '1TB SSD', '32GB', 'USA', 'In Stock'),
(3, 'Dell G15 Gaming', 435, 27000000, 'Gaming Laptop', 'Dell', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'USA', 'In Stock'),
(3, 'Microsoft Surface Pro 9', 345, 35000000, '2-in-1 Laptop', 'Microsoft', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'USA', 'In Stock'),
(3, 'HP Envy 13', 345, 22000000, 'Ultrabook', 'HP', 'Windows 11', 'Intel Core i5', '512GB SSD', '8GB', 'USA', 'In Stock'),
(3, 'MSI GE76 Raider', 345, 60000000, 'Gaming Laptop', 'MSI', 'Windows 11', 'Intel Core i9', '1TB SSD', '32GB', 'Taiwan', 'In Stock'),
(3, 'Lenovo Ideapad 5', 345, 19000000, 'Laptop', 'Lenovo', 'Windows 11', 'AMD Ryzen 5', '512GB SSD', '8GB', 'China', 'In Stock'),
(3, 'Acer Aspire 7', 567, 18000000, 'Laptop', 'Acer', 'Windows 11', 'Intel Core i5', '512GB SSD', '8GB', 'Taiwan', 'In Stock'),
(3, 'Asus TUF Gaming A15', 657, 30000000, 'Gaming Laptop', 'Asus', 'Windows 11', 'AMD Ryzen 7', '1TB SSD', '16GB', 'Taiwan', 'In Stock'),
(3, 'Dell Vostro 5402', 556, 21000000, 'Laptop', 'Dell', 'Windows 11', 'Intel Core i5', '512GB SSD', '8GB', 'USA', 'In Stock'),
(3, 'HP Elite Dragonfly', 743, 40000000, 'Business Laptop', 'HP', 'Windows 11', 'Intel Core i7', '1TB SSD', '16GB', 'USA', 'In Stock');


INSERT INTO product (suppliers_id, name, quality, price, genre, brand, operating_system, cpu, memory, ram, made_in, status)
VALUES 
(4, 'Asus ROG Strix Scar 15', 12, 42000000, 'Gaming Laptop', 'Asus', 'Windows 11', 'AMD Ryzen 9', '1TB SSD', '32GB', 'Taiwan', 'In Stock'),
(4, 'Acer Nitro 5', 8, 25000000, 'Gaming Laptop', 'Acer', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'Taiwan', 'In Stock'),
(4, 'Lenovo ThinkBook 14s', 15, 21000000, 'Business Laptop', 'Lenovo', 'Windows 11', 'AMD Ryzen 5', '512GB SSD', '8GB', 'China', 'In Stock'),
(4, 'MSI Prestige 14', 10, 31000000, 'Ultrabook', 'MSI', 'Windows 11', 'Intel Core i7', '512GB SSD', '16GB', 'Taiwan', 'In Stock'),
(4, 'HP ZBook Firefly 14', 7, 46000000, 'Workstation', 'HP', 'Windows 11', 'Intel Core i7', '1TB SSD', '32GB', 'USA', 'In Stock'),
(4, 'Dell Alienware M15 R6', 5, 55000000, 'Gaming Laptop', 'Dell', 'Windows 11', 'Intel Core i9', '1TB SSD', '32GB', 'USA', 'In Stock'),
(4, 'Apple MacBook Pro 14 M1', 9, 53000000, 'Laptop', 'Apple', 'macOS', 'Apple M1 Pro', '512GB SSD', '16GB', 'USA', 'In Stock'),
(4, 'Microsoft Surface Go 3', 18, 17000000, '2-in-1 Laptop', 'Microsoft', 'Windows 11', 'Intel Pentium Gold', '128GB SSD', '8GB', 'USA', 'In Stock'),
(4, 'Acer Swift X', 6, 20000000, 'Ultrabook', 'Acer', 'Windows 11', 'AMD Ryzen 7', '512GB SSD', '16GB', 'Taiwan', 'In Stock'),
(4, 'Lenovo Yoga 9i', 3, 45000000, '2-in-1 Laptop', 'Lenovo', 'Windows 11', 'Intel Core i7', '1TB SSD', '16GB', 'China', 'In Stock');


insert into manager(fullname,address,birthday,phone_number)
values ('James Nguyen','nlu tpHCM','09-09-2005','0398167244')
     ,('Henry Phan','nlu tpHCM','07-28-2005','0978234879')
     ,('Thaniel Nguyen','nlu tpHCM','04-21-2005','0936482635');


insert into account(username ,password,email,create_date, manage_id)
values
    ('admin','123','duynguyenavg@gmail.com','10-21-2024',1)
     ,('james','123','23130075@gmail.com','10-21-2024',2)
     ,('henry','123','23130117@gmail.com','10-21-2024',3)
     ,('thaniel','123','23130370@gmail.com','10-21-2024',4);
select *from account


