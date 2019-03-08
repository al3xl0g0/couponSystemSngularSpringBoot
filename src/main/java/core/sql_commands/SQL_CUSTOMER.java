package core.sql_commands;

public interface SQL_CUSTOMER {

    // Commands for CUSTOMER
    String CREATE = "INSERT INTO customer (customer_name, password, email) VALUES (?, ?, ?)";
    String DELETE = "DELETE FROM customer WHERE id = ?";
    String UPDATE = "UPDATE customer SET customer_name = ?, password = ?, email = ? WHERE id = ?";
    String SELECT = "SELECT * FROM customer";
    String SELECT_BY_ID = "SELECT * FROM customer WHERE id = ?";
    String SELECT_BY_NAME = "SELECT DISTINCT * FROM customer WHERE UPPER(customer_name) LIKE UPPER(?)";

    // Commands for CUSTOMER_COUPON
    String LINK_COUPON = "INSERT INTO customer_coupon (customer_id, coupon_id) VALUES (?,?)";
    String SELECT_COUPON = "SELECT coupon_id FROM customer_coupon WHERE coupon_id = ? AND customer_id = ?";
    String SELECT_COUPONS = "SELECT coupon_id FROM customer_coupon WHERE customer_id = ?";

}
