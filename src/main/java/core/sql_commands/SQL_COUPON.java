package core.sql_commands;

public interface SQL_COUPON {

    // Commands for COUPON
    String CREATE = "INSERT INTO coupon (title, start_date, end_date, amount, type, message, price, image, company_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String DELETE = "DELETE FROM coupon WHERE id = ?";
    String UPDATE = "UPDATE coupon SET title = ?, start_date = ?, end_date = ?, amount = ?, type = ?, message = ?, price = ?, image = ?, company_id = ? WHERE id = ?";
    String SELECT_ALL = "SELECT * FROM coupon";
    String SELECT_BY_ID = "SELECT * FROM coupon WHERE id = ?";
    String SELECT_BY_TITLE = "SELECT DISTINCT * FROM coupon WHERE UPPER(title) LIKE UPPER(?)";
    String SELECT_AVAILABLE = "SELECT * FROM coupon WHERE amount > 0";
    String DELETE_EXPIRED = "DELETE FROM coupon WHERE end_date < CURRENT_DATE";

}
