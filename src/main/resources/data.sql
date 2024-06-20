INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('John', 'Doe', 'john@gmail.com', 123456789, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 1000);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Alice', 'Smith', 'alice@gmail.com', 987654321, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 750);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Bob', 'Johnson', 'bob@gmail.com', 555123456, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 600);
INSERT INTO users(first_name, last_name, email, phone, password, total_amount) VALUES
    ('Sam', 'Alexis', 'imnotsam@gmail.com', 444123456, '$2a$12$JGriZzgFwZNEeuIzFcocjug9wb0/G0EJ1nco27FZoCvVLmfpfiiWe', 700);


INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Transport', '2024-06-12', '2024-06-30', 40);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Groceries', '2024-06-12', '2024-06-30', 300);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Shopping', '2024-06-01', '2024-06-30', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Bill', '2024-06-01', '2024-06-30', 100);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-06-01', '2024-06-30', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-01-01', '2024-01-30', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Utility', '2024-01-02', '2024-01-25', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-02-02', '2024-02-28', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-03-02', '2024-03-28', 200);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-04-02', '2024-04-28', 200);

INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Shopping', '2024-02-02', '2024-02-28', 300);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Vacation', '2024-02-02', '2024-02-28', 400);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-01-02', '2024-01-28', 300);

INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Shopping', '2024-02-02', '2024-02-28', 300);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Vacation', '2024-02-02', '2024-02-28', 400);
INSERT INTO groups (Category, Start_date, End_date, BAmount) VALUES ('Rent', '2024-01-02', '2024-01-28', 300);


update groups set user_id = 1
where Budget_Id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16);

update groups set user_id = 2
where Budget_Id IN (11, 12, 13);

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
