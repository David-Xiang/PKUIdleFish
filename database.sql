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
    type ENUM('buyer','forseller','seller','admin','deleted') DEFAULT 'buyer'
) AUTO_INCREMENT = 1000;

CREATE TABLE IF NOT EXISTS product(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    categoryid TINYINT UNSIGNED,
    title VARCHAR(128),
    imgsrc VARCHAR(256),
    price DECIMAL(10, 2),
    sellerid INT UNSIGNED,
    description TINYTEXT,
    saletime DATETIME,
    status ENUM('draft','sale','sold','returned','deleted') DEFAULT 'draft'
);

CREATE TABLE category(
    categoryid TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    categoryname VARCHAR(16)
);

CREATE TABLE forseller(
    forsellerid INT UNSIGNED,
    requesttime DATETIME
);

CREATE TABLE seller(
    sellerid INT UNSIGNED,
    approvetime DATETIME
);

CREATE TABLE cart(
    buyername VARCHAR(32),
    productid INT UNSIGNED
);

CREATE TABLE bought(
    buyername VARCHAR(32),
    productid INT UNSIGNED,
    boughttime DATETIME
);

CREATE TABLE comment(
    buyerid INT UNSIGNED,
    buyername VARCHAR(32),
    productid INT UNSIGNED,
    commenttime DATETIME,
    content TEXT
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

INSERT INTO account(username, en_passwd, birth, sex, email, phone, type) VALUES
('user1', myhash('passwd1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'forseller'),
('user2', myhash('passwd2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'seller'),
('user3', myhash('passwd3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'forseller'),
('user5', myhash('passwd5'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'seller'),
('user6', myhash('passwd6'), '2000-01-05', 'M', 'user5@pku.edu.cn', '10086000005', 'buyer'),
('user7', myhash('passwd7'), '2000-01-06', 'M', 'user6@pku.edu.cn', '10086000006', 'buyer');

INSERT INTO forseller(forsellerid, requesttime) VALUES
('1000', '2010-10-10 10:10:10'),
('1002', '2010-10-11 10:10:10');
INSERT INTO seller(sellerid, approvetime) VALUES
('1001', '2009-09-09 09:09:09'),
('1003', '2009-09-08 09:09:09');

INSERT INTO category (categoryid, categoryname) VALUES
('1', 'category1'),
('2', 'category2');
INSERT INTO product (categoryid, title, imgsrc, price, sellerid, description, saletime, status) VALUES
('1', 'title1', 'imgsrc1', '11', '1001', 'description1', '2018-10-01 00:00:00', 'sale'),
('2', 'title2', 'imgsrc2', '12', '1003', 'description2', '2018-10-02 00:00:00', 'sold'),
('1', 'title3', 'imgsrc3', '13', '1001', 'description3', '2018-10-03 00:00:00', 'sale'),
('1', 'title4', 'imgsrc4', '14', '1003', 'description4', '2018-10-04 00:00:00', 'sale'),
('2', 'title5', 'imgsrc5', '15', '1001', 'description5', '2018-10-05 00:00:00', 'draft');

INSERT INTO cart(productid, buyername) VALUES
('3', 'user7');
INSERT INTO bought(productid, buyername, boughttime) VALUES
('2', 'user7', '2019-10-01 16:00:00');