# Coupon System
Project of full-stack Java

About the project:  
There are three entities:
1. Admin – can create, read, update, delete companies, coupons and customers.
2. Company – can create, read, update, delete coupons.
2. Customer – can check available coupons, purchase them and check list of purchased coupons.

Used technologies:  
Backend: 
- Data access: JDBC.
- Servlet container: Apache Tomcat.
- Database: Apache Derby.
- Build tool: Maven.

Frontend:
- Framework: Angular 7.
- Libraries: animate.css, materialize-css.
- Build tool: Node.js NPM.

Instructions:  
You can build database by using class DatabaseBuilder from the package src.main.java.core.db_builder, go to "client" folder and run "npm i" to install Node dependecies, after that you have to run Derby server, Apache Tomcat server and run client side in Angular by using command “npm start”. You can login like admin(username and password – “admin”) or just to register new company or customer.

Apache Derby address: jdbc:derby://localhost:1527/coupon-system-db  
Apache Tomcat address: http://localhost:8080  
Angular address: http://localhost:4200  
