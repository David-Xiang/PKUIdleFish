-- TODO: name的约束：不可重复，不可以空格开头或结尾，及字数限制
DROP TABLE IF EXISTS account;
CREATE TABLE account(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE,
    passwd VARCHAR(16) NOT NULL,
    birth DATE NOT NULL,
    sex CHAR(1) NOT NULL,
    email VARCHAR(32) NOT NULL,
    phone BIGINT NOT NULL,
    state ENUM('buyer','forseller','seller','admin','deleted') NOT NULL DEFAULT 'buyer'
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci AUTO_INCREMENT = 1000;

DROP TABLE IF EXISTS cart;
CREATE TABLE cart(
    buyerid INT UNSIGNED NOT NULL,
    productid INT UNSIGNED NOT NULL
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS category;
CREATE TABLE category(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(16) NOT NULL
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS comment;
CREATE TABLE comment(
    buyerid INT UNSIGNED NOT NULL,
    productid INT UNSIGNED NOT NULL,
    time DATETIME NOT NULL,
    content TEXT NOT NULL
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS product;
CREATE TABLE IF NOT EXISTS product(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    categoryid TINYINT NOT NULL,
    title VARCHAR(128) NOT NULL,
    imgsrc VARCHAR(256) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    sellerid INT UNSIGNED NOT NULL,
    description TINYTEXT NOT NULL,
    saletime DATETIME NOT NULL,
    state ENUM('draft','sale','sold','returned','deleted') NOT NULL DEFAULT 'draft'
) ENGINE = InnoDB COLLATE = utf8mb4_general_ci;
