CREATE DATABASE IF NOT EXISTS login_db;
USE login_db;
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);
INSERT INTO users (username, password) VALUES ('admin', '1234');
