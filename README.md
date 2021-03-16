# three-reiver

## projects

### account-feed 
  on application start, it writes four account items into kafka topic, threeriver-account.  
  
  two of them have account number "abc".   
  two of them have account number "efg".   

  a kafka listener will write the account feed into the h2 in-memory db which 
  expose tcp port 10090.   

  assume kafka broker is availabe at localhost:9092  
  
  to run this app:  
    
  cd account-feed  
  mvn clean package -DskipTests   
  mvn spring-boot:run  

  to check the data: 
     
    http://localhost:8091/h2-console  
        jdbc url is jdbc:h2:mem:account_db;MODE=MYSQL  
        username is sa  
        password is password  
### account-api  

gets latest balance of an account. It pick up the data from jdbc:h2:tcp://localhost:10090/  mem:account_db;MODE=MYSQL.  

to run this app:
    
    cd account-api  
    mvn clean package  
    mvn spring-boot:run  
then:  
    curl http://localhost:8097/account/v1/abc  

### transaction-feed 
  on application start, it writes four transactions into kafka topic, threeriver-transaction.  
  two of them has account number "abc", "DEPOSIT" and "WITHDRAW" each.   
  two of them has account number "efg", "DEPOSIT" and "WITHDRAW" each.   
  
  a kafka listener will write the account feed into h2 in-memory db which 
  expose tcp port 10092.   

  assume kafka broker is availabe at localhost:9092  
  
  to run this app:  
    
  cd transaction-feed   
  mvn clean package -DskipTests   
  mvn spring-boot:run  

  to check the data:
    
    http://localhost:8092/h2-console
        jdbc url is jdbc:h2:mem:transaction_db;MODE=MYSQL  
        username is sa  
        password is password  
### transaction-api


to run this app:  
  
    mvn clean package  
    mvn spring-boot:run  
then:  

curl -d '{"accountNumber": "abc123","transactionType": "DEPOSIT","beginDate": "2019-12-31 10:10:10","endDate": "2021-12-31 10:10:10"}' -H "Content-Type: application/json" -X POST http://localhost:8096/transaction/v1/query  

curl -d '{"accountNumber": "abc123","beginDate": "2019-12-31 10:10:10","endDate": "2021-12-31 10:10:10"}' -H "Content-Type: application/json" -X POST http://localhost:8096/transaction/v1/query  
 

### account-feed-stream-api
an implementation of account feed using kafak stream using local store to keep the account feed. It worked but did not find time to test it again before submitting.