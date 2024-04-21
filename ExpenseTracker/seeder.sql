-- User Table Definition
CREATE DATABASE expense_tracker;
use expense_tracker;
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
    CONSTRAINT chk_email CHECK (email LIKE '%_@__%.__%')
);

-- Income Source Table Definition
CREATE TABLE IF NOT EXISTS income_sources (
    income_source_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    source_name VARCHAR(255) NOT NULL,
    annual_income DECIMAL(15, 2) NOT NULL CHECK (annual_income >= 0),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


-- Expense Table 

CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    expense_date DATE NOT NULL,
    expense_amount DECIMAL(10, 2) NOT NULL,
    expense_category ENUM('RENT','UTILITY','VEHICLE','GROCERY','EMI','MEDICAL','MISCELLANEOUS','TRAVEL','PERSONAL','SHOPPING','EDUCATION','FOOD') NOT NULL,
    expense_transaction_type ENUM('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'ONLINE','NET_BANKING') NOT NULL,
    expense_receipt LONGBLOB,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS expensegroups (
    group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS group_members (
        member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        group_id BIGINT,
         user_id BIGINT,
         FOREIGN KEY (group_id) REFERENCES expensegroups(group_id) ON DELETE CASCADE,
         FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
     );

CREATE TABLE IF NOT EXISTS groupexpenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    expense_category ENUM('RENT','UTILITY','VEHICLE','GROCERY','EMI','MEDICAL','MISCELLANEOUS','TRAVEL','PERSONAL','SHOPPING','EDUCATION','FOOD') NOT NULL,
    expense_date DATE NOT NULL,
    expense_amount DECIMAL(10, 2) NOT NULL CHECK (expense_amount >= 0),
    expense_transaction_type ENUM('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'ONLINE','NET_BANKING') NOT NULL,
    FOREIGN KEY (group_id) REFERENCES expensegroups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
    

-- Budget Table Definition
CREATE TABLE IF NOT EXISTS budgets (
    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category ENUM('RENT','UTILITY','VEHICLE','GROCERY','EMI','MEDICAL','MISCELLANEOUS','TRAVEL','PERSONAL','SHOPPING','EDUCATION','FOOD') NOT NULL,
    monthly_budget DECIMAL(10, 2) NOT NULL CHECK (monthly_budget >= 0),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);



INSERT INTO users (user_name, first_name, last_name, email, password, gender) VALUES 
('john_doe', 'John', 'Doe', 'john.doe@example.com', 'hashed_password1', 'MALE'),
('jane_doe', 'Jane', 'Doe', 'jane.doe@example.com', 'hashed_password2', 'FEMALE'),
('sam_smith', 'Sam', 'Smith', 'sam.smith@example.com', 'hashed_password3', 'OTHER'),
('alex_jones', 'Alex', 'Jones', 'alex.jones@example.com', 'hashed_password4', 'MALE'),
('lisa_white', 'Lisa', 'White', 'lisa.white@example.com', 'hashed_password5', 'FEMALE');


INSERT INTO income_sources (user_id, source_name, annual_income) VALUES 
(1, 'Software Development', 95000.00),
(2, 'Graphic Design', 65000.00),
(3, 'Freelance Writing', 40000.00),
(4, 'Digital Marketing', 72000.00),
(5, 'Consulting', 110000.00);


INSERT INTO budgets (user_id, category, monthly_budget) VALUES
(2, 'RENT', 1000.00),
(2, 'UTILITY', 150.00),
(2, 'VEHICLE', 200.00),
(2, 'GROCERY', 300.00),
(2, 'EMI', 500.00),
(2, 'MEDICAL', 100.00),
(2, 'MISCELLANEOUS', 75.00),
(2, 'TRAVEL', 250.00),
(2, 'PERSONAL', 100.00),
(2, 'SHOPPING', 150.00),


INSERT INTO expenses 
    (user_id, expense_date, expense_amount, expense_category, expense_transaction_type) 
VALUES
    (1, '2023-04-01', 1200.00, 'RENT', 'ONLINE'),
    (1, '2023-04-05', 300.00, 'UTILITY', 'NET_BANKING'),
    (1, '2023-04-10', 150.00, 'GROCERY', 'DEBIT_CARD'),
    (1, '2023-04-12', 450.00, 'VEHICLE', 'CREDIT_CARD'),
    (1, '2023-04-1', 2000.00, 'EMI', 'ONLINE'),
    (1, '2023-04-18', 500.00, 'MEDICAL', 'CASH'),
    (1, '2023-04-20', 700.00, 'TRAVEL', 'CREDIT_CARD'),
    (1, '2023-04-22', 250.00, 'PERSONAL', 'DEBIT_CARD'),
    (1, '2023-04-25', 120.00, 'FOOD', 'CASH'),
    (1, '2023-04-28', 1000.00, 'EDUCATION', 'NET_BANKING');


INSERT INTO income_sources (user_id, source_name, annual_income)
VALUES
    (1, 'Full-time Job', 50000.00),
    (1, 'Part-time Job', 15000.00),
    (1, 'Freelancing', 20000.00),
    (1, 'Dividends', 3000.00),
    (1, 'Rental Income', 12000.00);
