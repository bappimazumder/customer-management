INSERT INTO `customer_management`.`customer_account`
(
    account_number,
    full_name,
    date_of_birth,
    account_type,
    account_status,
    balance,
    last_transaction_date)
VALUES
    ('11111111111',
     'David Balme',
     '2023-12-31',
     "Saving Account",
     1,
     500000,
     NOW()),
    ('1111111112',
     'Julien Balme',
     '2023-12-31',
     "Saving Account",
     1,
     400000,
     NOW()),
    ('1111111113',
     'Illina',
     '2000-06-19',
     "Current Account",
     1,
     600000,
     NOW())
;
