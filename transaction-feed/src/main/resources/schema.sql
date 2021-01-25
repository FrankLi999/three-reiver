drop table transaction if exists;
CREATE TABLE transaction (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    ACCOUNT_NUMBER VARCHAR(64) NOT NULL,
    transaction_Ts timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    transaction_Type VARCHAR(32) NOT NULL,
    amount DECIMAL(10, 2),
    PRIMARY KEY (id)
);
