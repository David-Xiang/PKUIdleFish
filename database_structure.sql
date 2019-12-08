-- TODO: name的约束：不可以空格开头或结尾，及字数限制
-- TODO: date运算
DROP DATABASE IF EXISTS pkuidlefish;
CREATE DATABASE pkuidlefish;
USE pkuidlefish;

CREATE TABLE account(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    passwd VARCHAR(16) NOT NULL,
    birth DATE NOT NULL,
    sex CHAR(1) NOT NULL,
    email VARCHAR(32) NOT NULL,
    phone BIGINT NOT NULL,
    state ENUM('buyer','forseller','seller','admin','deleted') NOT NULL DEFAULT 'buyer',
    PRIMARY KEY (id)
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci AUTO_INCREMENT = 1000;

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
    state ENUM('draft','sale','sold','returned','deleted') NOT NULL DEFAULT 'draft',
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
