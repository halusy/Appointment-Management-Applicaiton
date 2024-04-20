# Appointment Management Application
## By: Nicholas Ryan

This is my Appointment Management Application. 


Coded in Java and using JDBC to interface with an SQL Database, it allows users to create, modify, and delete appointments, customers, and users, all stored in the SQL Database. 
The program implements functioning Login Portal, checking user input against credentials also stored in the SQL Database. 
The application detects the users timezone and adjusts many facets of the application accordingly, ensuring a smooth and cohesive experiance across timezones. 
The application also uses recourse bundles to translate the application into French if a French locale is detected. 

This code must be ran via an IDE as it has not been converted into a standalone application. You will also need to create and link an SQL databse. To do this, adjust the 
parameters in "src/main/java/com/example/AMP/helper/JDBC.java" to correspond with an SQL server following this ERD: 

[Database ERD.pdf](https://github.com/halusy/Appointment-Management-Applicaiton/files/15048902/Database.ERD.pdf)




