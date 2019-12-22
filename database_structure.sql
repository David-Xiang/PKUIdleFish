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
    sex ENUM('男', '女'),
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
    time CHAR(8)
);

-- 
-- Functions
-- 

CREATE FUNCTION myhash(raw VARCHAR(20))
RETURNS CHAR(128) DETERMINISTIC
RETURN SHA2(CONCAT('sha2', raw, 'pkuidlefish2019'), 512);

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
-- Views
--

CREATE VIEW buyer_related AS
SELECT buyer_name AS username, COUNT(DISTINCT transaction.seller_name) AS num_of_sellers, COUNT(*) AS num_of_items, SUM(transaction.price) as total_spent
FROM transaction INNER JOIN product USING (product_id)
GROUP BY buyer_name;

CREATE VIEW commented AS
SELECT account.username AS seller_name, COUNT(buyer_name) AS times
FROM account LEFT JOIN product ON username = seller_name LEFT JOIN comment USING (product_id)
WHERE account_status = 'seller'
GROUP BY account.username;

CREATE VIEW user_age_group AS
SELECT username, (CASE
    WHEN TIMESTAMPDIFF(YEAR, birth, CURDATE()) < 30 THEN '青年'
    WHEN TIMESTAMPDIFF(YEAR, birth, CURDATE()) > 50 THEN '老年'
    ELSE '中年' END) as age_group
FROM account;

CREATE VIEW audience AS
SELECT account.username AS seller_name, IFNULL(age_group, '暂无买家') as age_group, COUNT(DISTINCT buyer_name) AS num
FROM account LEFT JOIN transaction ON username = seller_name LEFT JOIN user_age_group ON buyer_name = user_age_group.username
GROUP BY account.username, age_group;
