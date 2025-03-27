CREATE database marketplace;

use marketplace;

-- Tạo dữ liệu mẫu cho bảng Category
INSERT INTO category (category_id, category_name) VALUES
(1, 'Điện thoại'),
(2, 'Laptop'),
(3, 'Đồng hồ'),
(4, 'Phụ kiện công nghệ');

-- Tạo dữ liệu mẫu cho bảng Product
INSERT INTO product (owner_id, product_name, category_id, original_price, current_price, quantity, origin, warranty, product_condition, condition_description, product_description, initial_image, current_image, created_at, sold) VALUES
(1, 'iPhone 13 Pro Max', 1, '30000000', '27000000', 5, 'USA', '12 tháng', 'Cũ', 'Máy đẹp 99%', 'iPhone 13 Pro Max like new', 'iphone13_old.jpg', 'iphone13_new.jpg', NOW() - INTERVAL 2 DAY, 10),
(2, 'Samsung Galaxy S22 Ultra', 1, '25000000', '23000000', 3, 'Korea', '12 tháng', 'Cũ', 'Không trầy xước', 'Samsung S22 Ultra fullbox', 's22_old.jpg', 's22_new.jpg', NOW() - INTERVAL 5 DAY, 8),
(3, 'MacBook Pro M1', 2, '35000000', '32000000', 7, 'USA', '24 tháng', 'Cũ', 'Máy còn bảo hành', 'MacBook Pro M1 2021', 'macbook_old.jpg', 'macbook_new.jpg', NOW() - INTERVAL 10 DAY, 15),
(4, 'Dell XPS 15', 2, '40000000', '38000000', 4, 'USA', '24 tháng', 'Cũ', 'Chạy mượt, pin tốt', 'Dell XPS 15 2022', 'xps_old.jpg', 'xps_new.jpg', NOW() - INTERVAL 7 DAY, 12),
(5, 'Apple Watch Series 7', 3, '10000000', '9000000', 6, 'USA', '12 tháng', 'Cũ', 'Hàng đẹp 98%', 'Apple Watch Series 7 GPS', 'awatch_old.jpg', 'awatch_new.jpg', NOW() - INTERVAL 1 DAY, 5),
(6, 'Samsung Galaxy Watch 4', 3, '8000000', '7500000', 8, 'Korea', '12 tháng', 'Cũ', 'Như mới', 'Galaxy Watch 4 LTE', 'gwatch_old.jpg', 'gwatch_new.jpg', NOW() - INTERVAL 3 DAY, 7),
(7, 'Tai nghe AirPods Pro', 4, '5000000', '4500000', 10, 'USA', '12 tháng', 'Cũ', 'Fullbox, sạch đẹp', 'Tai nghe AirPods Pro', 'airpods_old.jpg', 'airpods_new.jpg', NOW() - INTERVAL 4 DAY, 20),
(8, 'Tai nghe Sony WH-1000XM4', 4, '6000000', '5700000', 9, 'Japan', '12 tháng', 'Cũ', 'Nguyên zin', 'Tai nghe Sony WH-1000XM4', 'sonywh_old.jpg', 'sonywh_new.jpg', NOW() - INTERVAL 2 DAY, 18),
(9, 'iPad Air 4', 1, '18000000', '17000000', 3, 'USA', '12 tháng', 'Cũ', 'Máy zin 100%', 'iPad Air 4 WiFi 64GB', 'ipad_old.jpg', 'ipad_new.jpg', NOW() - INTERVAL 6 DAY, 6),
(10, 'MacBook Air M2', 2, '32000000', '30000000', 5, 'USA', '24 tháng', 'Cũ', 'Máy mới 99%', 'MacBook Air M2 2022', 'mba_old.jpg', 'mba_new.jpg', NOW() - INTERVAL 3 DAY, 9),
(11, 'Logitech MX Master 3', 4, '3000000', '2800000', 12, 'USA', '24 tháng', 'Cũ', 'Hàng chuẩn', 'Chuột Logitech MX Master 3', 'logi_old.jpg', 'logi_new.jpg', NOW() - INTERVAL 5 DAY, 14),
(12, 'Bàn phím cơ Keychron K2', 4, '2500000', '2300000', 10, 'China', '12 tháng', 'Cũ', 'Hàng fullbox', 'Bàn phím cơ Keychron K2 RGB', 'keychron_old.jpg', 'keychron_new.jpg', NOW() - INTERVAL 2 DAY, 13),
(13, 'Samsung Galaxy Tab S8', 1, '22000000', '21000000', 4, 'Korea', '12 tháng', 'Cũ', 'Máy như mới', 'Samsung Galaxy Tab S8', 'tabs8_old.jpg', 'tabs8_new.jpg', NOW() - INTERVAL 1 DAY, 4),
(14, 'GoPro Hero 10', 4, '15000000', '14000000', 7, 'USA', '12 tháng', 'Cũ', 'Fullbox', 'Camera GoPro Hero 10', 'gopro_old.jpg', 'gopro_new.jpg', NOW() - INTERVAL 2 DAY, 11);


