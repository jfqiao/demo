CREATE DATABASE IF NOT EXISTS db_demo DEFAULT CHAR SET UTF8;

USE db_demo;

CREATE TABLE IF NOT EXISTS t_user (
	user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(32) NOT NULL,
	user_age INT NOT NULL,
	user_balance INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS t_book (
	book_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	book_name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS r_user_book (
	r_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	book_id INT NOT NULL
);