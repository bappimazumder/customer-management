# customer-managment

This repo will contain customer management APIs.


**Prerequisite: **
*  openjdk 17
*  mysql 
*  git client


**Prepare Database: **

    CREATE USER 'bappi'@'localhost' IDENTIFIED BY '123456';
    CREATE DATABASE customer_management;
    GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'bappi'@'localhost' WITH GRANT OPTION;
   


now as we have our db created we need to run the db script

1. resources/db_script/s1_customer_account_create.sql
 
2.  resources/db_script/s2_customer_account_insert.sql

Build the war file using this command 
 
 ./gradlew clean bootWar 
 
The war will found on \build\libs\customer-management.war
 
 Finally, copy the generated war to /path/to/tomcat10/webapps/ directory as customer-management.war


For test please export the postman collection from this location
resources/postman_collection/CustomerAccountAPI Collection.postman_collection.json

Here is curl 
===============
1. For Get Account Details
curl --location 'http://localhost:8085/api/v1/customer/account/getDetails?accountNumber=11111111111'

2. For Transfer amount
   curl --location 'http://localhost:8085/api/v1/customer/account/transferAmount' \
   --header 'Content-Type: application/json' \
   --data '{
   "senderAccountNo":"11111111111",
   "receiverAccountNo":"1111111112",
   "amount": 500
   }'
 