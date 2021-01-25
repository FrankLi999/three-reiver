drop table account IF EXISTS;
CREATE TABLE account (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    accountNumber VARCHAR(64) NOT NULL,
    lastUpdateTimstamp timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    blance DECIMAL(10, 2),
    PRIMARY KEY (id)
);
