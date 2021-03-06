drop table account if exists;
CREATE TABLE account (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    ACCOUNT_NUMBER VARCHAR(64) NOT NULL,
    LAST_UPDATE_TIMSTAMP timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    BALANCE DECIMAL(10, 2),
    PRIMARY KEY (id)
);