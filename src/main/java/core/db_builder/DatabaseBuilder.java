package core.db_builder;

import core.exceptions.CouponSystemException;
import core.sql_commands.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public static void main(String[] args) throws CouponSystemException {

        try (Connection connection = DriverManager.getConnection(SQL.URL + ";create=true");
             Statement stmt = connection.createStatement()) {

            // Creating table COMPANY in database
            String company = "CREATE TABLE ";
            company += "company(id BIGINT PRIMARY KEY GENERATED "
                    + "ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), ";
            company += "company_name VARCHAR(20) NOT NULL, ";
            company += "password VARCHAR(20) NOT NULL, ";
            company += "email VARCHAR(20) NOT NULL)";

            stmt.executeUpdate(company);

            // Creating table COUPON in database
            String coupon = "CREATE TABLE ";
            coupon += "coupon(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), ";
            coupon += "title VARCHAR(20) NOT NULL, ";
            coupon += "start_date DATE NOT NULL, ";
            coupon += "end_date DATE NOT NULL, ";
            coupon += "amount INT NOT NULL, ";
            coupon += "type VARCHAR(20) NOT NULL, ";
            coupon += "message VARCHAR(20) NOT NULL, ";
            coupon += "price FLOAT NOT NULL, ";
            coupon += "image VARCHAR(20) NOT NULL, ";
            coupon += "company_id BIGINT NOT NULL DEFAULT 0, FOREIGN KEY(company_id) "
                    + "REFERENCES company(id) ON DELETE CASCADE)";

            stmt.executeUpdate(coupon);

            // Creating table CUSTOMER in database
            String customer = "CREATE TABLE ";
            customer += "customer(id BIGINT PRIMARY KEY GENERATED "
                    + "ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), ";
            customer += "customer_name VARCHAR(20) NOT NULL, ";
            customer += "password VARCHAR(20) NOT NULL, ";
            customer += "email VARCHAR(20) NOT NULL)";

            stmt.executeUpdate(customer);

            // Creating table CUSTOMER_COUPON in database
            String customerCoupon = "CREATE TABLE ";
            customerCoupon += "customer_coupon(customer_id BIGINT NOT NULL, ";
            customerCoupon += "coupon_id BIGINT NOT NULL, "
                    + "FOREIGN KEY(customer_id) REFERENCES customer(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY(coupon_id) REFERENCES coupon(id) ON DELETE CASCADE)";

            stmt.executeUpdate(customerCoupon);

        } catch (SQLException e) {
            throw new CouponSystemException("SQL exception. ", e);
        }
    }
}
