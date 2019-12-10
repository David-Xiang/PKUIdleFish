-- TODO: name的约束：不可以空格开头或结尾，及字数限制
-- TODO: 添加索引
-- 检查上架时间是否超过5年（1800天): SET now = CURDATE(); DATEDIFF(now, ?) < 1800

-- 
-- Structure
-- 

DROP DATABASE IF EXISTS pkuidlefish;
CREATE DATABASE pkuidlefish;
USE pkuidlefish;

CREATE TABLE account(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) UNIQUE,
    en_passwd CHAR(128),
    birth DATE,
    sex CHAR(1),
    email VARCHAR(32),
    phone BIGINT,
    accountstatus ENUM('buyer','forseller','seller','admin','deleted') DEFAULT 'buyer',
    accupdatetime DATETIME DEFAULT CURRENT_TIMESTAMP
) AUTO_INCREMENT = 1000;

CREATE TABLE category(
    categoryid TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    categoryname VARCHAR(16)
);

CREATE TABLE product(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    categoryid TINYINT UNSIGNED,
    title VARCHAR(128),
    imgsrc VARCHAR(256),
    price DECIMAL(10, 2),
    sellerid INT UNSIGNED,
    sellername VARCHAR(32),
    description TINYTEXT,
    productstatus ENUM('draft','sale','sold','returned','deleted') DEFAULT 'draft',
    produpdatetime DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaction(
    buyerid VARCHAR(32),
    buyername VARCHAR(32),
    productid INT UNSIGNED,
    transactionstatus ENUM('cart', 'bought', 'returned'),
    transupdatetime DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment(
    userid INT UNSIGNED,
    username VARCHAR(32),
    productid INT UNSIGNED,
    content TEXT,
    commenttime DATETIME
);

-- 
-- Functions
-- 

CREATE FUNCTION myhash(original VARCHAR(16))
RETURNS CHAR(128) DETERMINISTIC
RETURN SHA2(CONCAT('sha2', original, 'pkuidlefish2019'), 512);

-- 
-- Data
-- 

INSERT INTO account(username, en_passwd, birth, sex, email, phone, accountstatus, accupdatetime) VALUES
('user1', myhash('passwd1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'forseller', '2011-11-11'),
('user2', myhash('passwd2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'seller', '2010-10-10'),
('user3', myhash('passwd3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'forseller', '2009-09-09'),
('user5', myhash('passwd5'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'seller', '2008-08-08'),
('user6', myhash('passwd6'), '2000-01-05', 'M', 'user5@pku.edu.cn', '10086000005', 'buyer', '2006-06-06'),
('user7', myhash('passwd7'), '2000-01-06', 'M', 'user6@pku.edu.cn', '10086000006', 'buyer', '2007-07-07');

INSERT INTO category (categoryid, categoryname) VALUES
('1', 'category1'),
('2', 'category2');
INSERT INTO product (categoryid, title, imgsrc, price, sellerid, sellername, description, produpdatetime, productstatus) VALUES
('1', 'title1', 'imgsrc1', '11', '1001', 'user1', 'description1', '2018-10-01', 'sale'),
('2', 'title2', 'imgsrc2', '12', '1003', 'user3', 'description2', '2018-10-02', 'sold'),
('1', 'title3', 'imgsrc3', '13', '1003', 'user3', 'description3', '2018-10-03', 'sale'),
('1', 'title4', 'imgsrc4', '14', '1003', 'user3', 'description4', '2018-10-04', 'sale'),
('2', 'title5', 'imgsrc5', '15', '1001', 'user1', 'description5', '2018-10-05', 'draft');

INSERT INTO transaction(productid, buyerid, buyername, transupdatetime, transactionstatus) VALUES
('3', '1007', 'user7', '2019-05-05', 'cart'),
('2', '1007', 'user7', '2019-10-01', 'bought');