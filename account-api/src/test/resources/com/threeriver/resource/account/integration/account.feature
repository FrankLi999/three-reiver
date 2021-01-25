Feature: get latest balance of an account
  Scenario: get latest balance with provided account number
    Given the client has the following account data
      |abc|2020-01-01 10:10:10|89.1|
      |abc|2021-01-01 10:10:10|12.5|
      |efg|2021-01-01 10:10:10|20.12|
      |efg|2020-01-01 10:10:10|60.3|
    When the user calls account api with the <accountNumber>
    Then the client receives status code of 200
    And the client receives the latest <blance>

    Examples:
    |accountNumber|blance
    |abc|12.5
    |efg|20.12
  Scenario: not found
    When the user calls account api with xyz that does not exist
    Then the client receives status code of 404
