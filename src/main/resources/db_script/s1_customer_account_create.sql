CREATE TABLE customer_account (
                                  id bigint NOT NULL AUTO_INCREMENT,
                                  account_number varchar(255),
                                  full_name VARCHAR(255),
                                  date_of_birth DATE,
                                  account_type TINYTEXT,
                                  account_status BOOLEAN,
                                  balance DOUBLE,
                                  last_transaction_date TIMESTAMP,
                                  PRIMARY KEY (id),
                                  UNIQUE KEY UK_customer_account_account_number (account_number)
);
