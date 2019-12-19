-- 
-- Structure
-- 

CREATE OR REPLACE DATABASE pkuidlefish;
USE pkuidlefish;

CREATE TABLE account(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) UNIQUE,
    en_password CHAR(128),
    birth DATE,
    sex ENUM('M', 'F'),
    email VARCHAR(32),
    phone CHAR(11),
    account_status ENUM('buyer','forseller','seller','admin','deleted') DEFAULT 'buyer',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1000;

CREATE TABLE category(
    category TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(16)
);

CREATE TABLE product(
    product_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    category TINYINT UNSIGNED,
    title VARCHAR(128),
    imgsrc VARCHAR(256),
    price DECIMAL(10, 2),
    seller_name VARCHAR(32),
    description TINYTEXT,
    product_status ENUM('draft','sale','sold','returned','deleted') DEFAULT 'draft',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bargain(
    buyer_name VARCHAR(32),
    product_id INT UNSIGNED,
    bargain_status ENUM('cart', 'done', 'canceled') DEFAULT 'cart'
);

CREATE TABLE transaction(
    buyer_name INT UNSIGNED,
    seller_name INT UNSIGNED,
    product_id INT UNSIGNED,
    price DECIMAL(10, 2),
    time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment(
    buyer_name VARCHAR(32),
    product_id INT UNSIGNED,
    content TINYTEXT,
    time DATE
);

-- 
-- Functions
-- 

CREATE FUNCTION myhash(raw VARCHAR(16))
RETURNS CHAR(128) DETERMINISTIC
RETURN SHA2(CONCAT('sha2', raw, 'pkuidlefish2019'), 512);

--
-- Views
--

CREATE VIEW spent AS
SELECT buyer_name, sum(transaction.price) as amount
FROM transaction INNER JOIN product USING (product_id)
GROUP BY buyer_name;

--
-- Triggers
--

DELIMITER $

CREATE TRIGGER generate_transaction
AFTER UPDATE ON bargain
FOR EACH ROW
BEGIN
  DECLARE seller_name VARCHAR(32);
  DECLARE price DECIMAL(10,2);
  IF old.bargain_status = 'cart' AND new.bargain_status = 'done' THEN
    SELECT seller_name, price INTO seller_name, price FROM product WHERE product_id = new.product_id;
    INSERT INTO transaction(buyer_name, seller_name, product_id, price, time) VALUES
    (buyer_name, seller_name, new.product_id, price);
  END IF;
END$

DELIMITER ;

-- 
-- Data
-- 

INSERT INTO account(username, en_password, birth, sex, email, phone, account_status, update_time) VALUES
('user1', myhash('passwd1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'forseller', '2011-11-11'),
('user2', myhash('passwd2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'seller', '2010-10-10'),
('user3', myhash('passwd3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'forseller', '2009-09-09'),
('user5', myhash('passwd5'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'seller', '2008-08-08'),
('user6', myhash('passwd6'), '2000-01-05', 'M', 'user5@pku.edu.cn', '10086000005', 'buyer', '2006-06-06'),
('user7', myhash('passwd7'), '2000-01-06', 'M', 'user6@pku.edu.cn', '10086000006', 'buyer', '2007-07-07');

INSERT INTO category (category, category_name) VALUES
('1', 'category1'),
('2', 'category2');
INSERT INTO product (category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
('1', 'title1', 'imgsrc1', '11', 'user1', 'description1', '2018-10-01', 'sale'),
('2', 'title2', 'imgsrc2', '12', 'user3', 'description2', '2018-10-02', 'sold'),
('1', 'title3', 'imgsrc3', '13', 'user3', 'description3', '2018-10-03', 'sale'),
('1', 'title4', 'imgsrc4', '14', 'user3', 'description4', '2018-10-04', 'sale'),
('2', 'title5', 'imgsrc5', '15', 'user1', 'description5', '2018-10-05', 'draft');

INSERT INTO bargain(product_id, buyer_name, bargain_status) VALUES
('3', 'user1', 'cart'),
('2', 'user1', 'cart');

UPDATE bargain
SET bargain_status = 'cart'
WHERE product_id = '3' AND buyer_name = 'user1';