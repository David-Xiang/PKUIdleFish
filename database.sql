-- 
-- Structure
-- 

CREATE OR REPLACE DATABASE pkuidlefish;
USE pkuidlefish;

CREATE TABLE account(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    en_password CHAR(128),
    birth DATE,
    sex ENUM('M', 'F'),
    email VARCHAR(50),
    phone CHAR(11),
    account_status ENUM('buyer','forseller','seller','admin','deleted') DEFAULT 'buyer',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1000;

CREATE TABLE category(
    category TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(20)
);

CREATE TABLE product(
    product_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    category TINYINT UNSIGNED,
    title VARCHAR(200),
    imgsrc VARCHAR(500),
    price DECIMAL(10, 2),
    seller_name VARCHAR(50),
    description VARCHAR(1000),
    product_status ENUM('draft','sale','sold','returned','deleted') DEFAULT 'draft',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    hot INT UNSIGNED DEFAULT 0
);

CREATE TABLE bargain(
    buyer_name VARCHAR(50),
    product_id INT UNSIGNED,
    bargain_status ENUM('cart', 'done') DEFAULT 'cart',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`buyer_name`,`product_id`)
);

CREATE TABLE transaction(
    transaction_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    buyer_name VARCHAR(50),
    seller_name VARCHAR(50),
    product_id INT UNSIGNED,
    price DECIMAL(10, 2),
    time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment(
    buyer_name VARCHAR(50),
    product_id INT UNSIGNED,
    content VARCHAR(500),
    time DATE DEFAULT CURRENT_DATE
);

-- 
-- Functions
-- 

CREATE FUNCTION myhash(raw VARCHAR(20))
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

CREATE TRIGGER set_product_sold
AFTER UPDATE ON bargain
FOR EACH ROW
BEGIN
  UPDATE product SET product_status = 'sold' WHERE product_id = new.product_id;
END$

CREATE TRIGGER set_product_returned
AFTER DELETE ON bargain
FOR EACH ROW
BEGIN
  IF old.bargain_status = 'done' THEN
    UPDATE product SET product_status = 'returned' WHERE product_id = old.product_id;
  END IF;
END$

CREATE TRIGGER auto_generate_transaction
AFTER UPDATE ON bargain
FOR EACH ROW
BEGIN
  DECLARE transaction_seller_name VARCHAR(50);
  DECLARE transaction_price DECIMAL(10,2);
  SELECT seller_name, price INTO transaction_seller_name, transaction_price FROM product WHERE product_id = new.product_id;
  INSERT INTO transaction(buyer_name, seller_name, product_id, price) VALUES
  (new.buyer_name, transaction_seller_name, new.product_id, transaction_price);
END$

CREATE TRIGGER increase_hot
AFTER INSERT ON bargain
FOR EACH ROW
BEGIN
  UPDATE product SET hot = hot + 1 WHERE product_id = new.product_id;
END$

CREATE TRIGGER decrease_hot
AFTER DELETE ON bargain
FOR EACH ROW
BEGIN
  UPDATE product SET hot = hot - 1 WHERE product_id = old.product_id;
END$

DELIMITER ;

-- 
-- Data
-- 

INSERT INTO account(username, en_password, birth, sex, email, phone, account_status, update_time) VALUES
('admin', myhash('admin'), '2000-01-05', 'M', 'admin@pku.edu.cn', '10086000005', 'admin', '2006-06-06'),
('user1', myhash('password1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'seller', '2011-11-11'),
('user2', myhash('password2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'forseller', '2010-10-10'),
('user3', myhash('password3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'seller', '2009-09-09'),
('user4', myhash('password4'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'buyer', '2008-08-08'),
('user5', myhash('password5'), '2000-01-05', 'M', 'user5@pku.edu.cn', '10086000005', 'buyer', '2007-07-07');

INSERT INTO category (category, category_name) VALUES
(1, 'category1'),
(2, 'category2');
INSERT INTO product (category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
(1, 'title1', 'imgsrc1', 11, 'user1', 'description1', '2018-10-01', 'sale'),
(2, 'title2', 'imgsrc2', 12, 'user3', 'description2', '2018-10-02', 'sale'),
(1, 'title3', 'imgsrc3', 13, 'user3', 'description3', '2018-10-03', 'sale'),
(1, 'title4', 'imgsrc4', 14, 'user3', 'description4', '2018-10-04', 'sale'),
(2, 'title5', 'imgsrc5', 15, 'user1', 'description5', '2018-10-05', 'draft');

INSERT INTO bargain(buyer_name, product_id) VALUES
('user1', 1),
('user1', 2),
('user1', 3),
('user2', 1),
('user2', 4),
('user2', 5),
('user3', 2);

UPDATE bargain
SET bargain_status = 'done'
WHERE buyer_name = 'user1' AND product_id = 1;

UPDATE bargain
SET bargain_status = 'done'
WHERE buyer_name = 'user3' AND product_id = 2;

INSERT INTO comment(product_id, buyer_name, content) VALUES
(1, 'user1', 'good'),
(1, 'user1', 'very good'),
(1, 'user1', 'very very very good');