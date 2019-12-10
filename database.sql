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
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    en_passwd CHAR(128) NOT NULL,
    birth DATE NOT NULL,
    sex CHAR(1) NOT NULL,
    email VARCHAR(32) NOT NULL,
    phone BIGINT NOT NULL,
    type ENUM('buyer','forseller','seller','admin','deleted') NOT NULL DEFAULT 'buyer',
    PRIMARY KEY (id)
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci AUTO_INCREMENT = 1000;

CREATE TABLE forseller(
    forsellerid INT UNSIGNED NOT NULL,
    requesttime DATETIME NOT NULL,
    PRIMARY KEY (forsellerid),
    CONSTRAINT fk_forseller_forsellerid FOREIGN KEY (forsellerid)
    REFERENCES account(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

CREATE TABLE seller(
    sellerid INT UNSIGNED NOT NULL,
    approvetime DATETIME NOT NULL,
    PRIMARY KEY (sellerid),
    CONSTRAINT fk_seller_sellerid FOREIGN KEY (sellerid)
    REFERENCES account(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

CREATE TABLE category(
    id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(16) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS product(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    categoryid TINYINT UNSIGNED NOT NULL,
    title VARCHAR(128) NOT NULL,
    imgsrc VARCHAR(256) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    sellerid INT UNSIGNED NOT NULL,
    description TINYTEXT NOT NULL,
    saletime DATETIME NOT NULL,
    status ENUM('draft','sale','sold','returned','deleted') NOT NULL DEFAULT 'draft',
    PRIMARY KEY (id),
    CONSTRAINT fk_product_categoryid FOREIGN KEY (categoryid)
    REFERENCES category(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_product_sellerid FOREIGN KEY (sellerid)
    REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

CREATE TABLE cart(
    buyerid INT UNSIGNED NOT NULL,
    productid INT UNSIGNED NOT NULL,
    CONSTRAINT fk_cart_buyerid FOREIGN KEY (buyerid)
    REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_cart_productid FOREIGN KEY (productid)
    REFERENCES product(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

CREATE TABLE comment(
    buyerid INT UNSIGNED NOT NULL,
    productid INT UNSIGNED NOT NULL,
    time DATETIME NOT NULL,
    content TEXT NOT NULL,
    CONSTRAINT fk_comment_buyerid FOREIGN KEY (productid)
    REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_comment_productid FOREIGN KEY (productid)
    REFERENCES product(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

-- 
-- Functions
-- 

CREATE FUNCTION myhash(original VARCHAR(16))
RETURNS CHAR(128) DETERMINISTIC
RETURN SHA2(CONCAT('sha2', original, 'pkuidlefish2019'), 512);

-- 
-- Data
-- 

INSERT INTO account(name, en_passwd, birth, sex, email, phone, type)
VALUES ('user1', myhash('passwd1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'forseller');
INSERT INTO account(name, en_passwd, birth, sex, email, phone, type)
VALUES ('user2', myhash('passwd2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'seller');
INSERT INTO account(name, en_passwd, birth, sex, email, phone, type)
VALUES ('user3', myhash('passwd3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'forseller');
INSERT INTO account(name, en_passwd, birth, sex, email, phone, type)
VALUES ('user4', myhash('passwd4'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'seller');

INSERT INTO forseller(forsellerid, requesttime)
VALUES ('1000', '2010-10-10 10:10:10');
INSERT INTO seller(sellerid, approvetime)
VALUES ('1001', '2009-09-09 09:09:09');
INSERT INTO forseller(forsellerid, requesttime)
VALUES ('1002', '2010-10-11 10:10:10');
INSERT INTO seller(sellerid, approvetime)
VALUES ('1003', '2009-09-08 09:09:09');