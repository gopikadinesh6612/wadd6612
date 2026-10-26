CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;
CREATE TABLE IF NOT EXISTS employees (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    basic_salary DOUBLE NOT NULL,
    allowance DOUBLE NOT NULL,
    deduction DOUBLE NOT NULL,
    gross_salary DOUBLE NOT NULL,
    net_salary DOUBLE NOT NULL
);
