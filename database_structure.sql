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
