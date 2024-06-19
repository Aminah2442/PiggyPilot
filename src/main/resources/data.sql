INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('John', 'Doe', 'john@gmail.com', 123456789, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 1000);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Alice', 'Smith', 'alice@gmail.com', 987654321, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 750);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Bob', 'Johnson', 'bob@gmail.com', 555123456, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 600);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Sam', 'Alexis', 'imnotsam@gmail.com', 444123456, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 700);


INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'TRANSPORT ', '  '), CONCAT(' ', '2024-06-12', ' '), CONCAT(' ', '2024-06-30', ' '), 40);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'GROCERIES', '   '), CONCAT(' ', '2024-06-12', ' '), CONCAT(' ', '2024-06-30', ' '), 300);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'SHOPPING', '   '), CONCAT(' ', '2024-06-01', ' '), CONCAT(' ', '2024-06-30', ' '), 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'BILL', '   '), CONCAT(' ', '2024-06-01', ' '), CONCAT(' ', '2024-06-30', ' '), 100);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'RENT', '   '), CONCAT(' ', '2024-06-01', ' '), CONCAT(' ', '2024-06-30', ' '), 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'RENT', '   '), CONCAT(' ', '2024-01-01', ' '), CONCAT(' ', '2024-01-30', ' '), 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'UTILITY', '   '), CONCAT(' ', '2024-01-02', ' '), CONCAT(' ', '2024-01-25', ' '), 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES (CONCAT('', 'RENT', '   '), CONCAT(' ', '2024-02-02', ' '), CONCAT(' ', '2024-02-28', ' '), 200);


update groups set user_id = 1
where Budget_Id = 1;

update groups set user_id = 2
where Budget_Id=2;

update groups set user_id = 3
where Budget_Id=3;

update groups set user_id = 4
where Budget_Id = 4;

INSERT INTO transactions (payment_method, note, date, amount, budget_id)
VALUES ('Cash', 'Dinner with friends', '2024-06-10', 50.00, 1);

INSERT INTO transactions (payment_method, note, date, amount, budget_id)
VALUES ('Debit', 'Online shopping', '2024-06-20', 149.95, 1);

INSERT INTO transactions (payment_method, note, date, amount, budget_id)
VALUES ('Cash', 'Lunch at work', '2024-06-05', 15.00, 1);

INSERT INTO transactions (payment_method, note, date, amount, budget_id)
VALUES ('Debit', 'Grocery shopping', '2024-06-15', 75.25, 1);

INSERT INTO transactions (payment_method, note, date, amount, budget_id)
VALUES ('Cash', 'Movie night', '2024-06-25', 20.00, 1);
