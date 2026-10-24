-- Hibernate (ddl-auto=update) creates these automatically; provided for the project report.
CREATE DATABASE IF NOT EXISTS bookrec_db;
USE bookrec_db;
CREATE TABLE users   (id BIGINT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255) NOT NULL UNIQUE);
CREATE TABLE books   (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL,
                      genre VARCHAR(255) NOT NULL, published_year INT, description VARCHAR(1000));
CREATE TABLE ratings (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, book_id BIGINT NOT NULL, score INT NOT NULL,
                      UNIQUE (user_id, book_id),
                      FOREIGN KEY (user_id) REFERENCES users(id), FOREIGN KEY (book_id) REFERENCES books(id));
