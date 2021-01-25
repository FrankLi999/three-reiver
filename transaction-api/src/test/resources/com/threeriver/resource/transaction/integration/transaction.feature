Feature: get transactions of an account
  Scenario: get all transaction between two dates
    Given the client has the following transaction data
      |abc|2020-01-01 10:10:10|WITHDRAW|89.1|
      |abc|2021-01-01 10:10:10|DEPOSIT|110.1|
      |efg|2021-01-01 10:10:10|DEPOSIT|20.12|
      |efg|2020-01-01 10:10:10|WITHDRAW|60.3|
    When the user call transaction api with the <accountNumber>, <beginDate> and <endDate> 
    Then the client receives status code of 200
    And the client receives <count> transactions

    Examples:
    |accountNumber|beginDate|endDate|count
    |abc|2019-12-21 10:09:10|2021-01-02 10:09:10|2
    |efg|2019-12-21 10:09:10|2021-01-02 10:09:10|2
  Scenario: not found
    When the user call transaction api with xyz that does not exist
    Then the client receives status code of 404
  Scenario: get all transaction of a trannsaction type between two dates
    Given the client has the following transaction data
      |abc|2020-01-01 10:10:10|WITHDRAW|89.1|
      |abc|2021-01-01 10:10:10|DEPOSIT|110.1|
      |efg|2021-01-01 10:10:10|DEPOSIT|20.12|
      |efg|2020-01-01 10:10:10|WITHDRAW|60.3|
    When the user call transaction api with the <accountNumber>, <transactionType>, <beginDate> and <endDate> 
    Then the client receives status code of 200
    And the client receives <count> transactions

    Examples:
    |accountNumber|transactionType|beginDate|endDate|count
    |abc|WITHDRAW|2019-12-21 10:09:10|2021-01-02 10:09:10|2
    |efg|DEPOSIT|2019-12-21 10:09:10|2021-01-02 10:09:10|2
  