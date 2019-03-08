package core.sql_commands;

public interface SQL_COMPANY {

	// Commands for COMPANY
	String CREATE = "INSERT INTO company (company_name, password, email) VALUES (?, ?, ?)";
	String DELETE = "DELETE FROM company WHERE id = ?";
	String UPDATE = "UPDATE company SET company_name = ?, password = ?, email = ? WHERE id = ?";
	String SELECT = "SELECT * FROM company";
	String SELECT_BY_ID = "SELECT * FROM company WHERE id = ?";
	String SELECT_BY_NAME = "SELECT DISTINCT * FROM company WHERE UPPER(company_name) LIKE UPPER(?)";

	// Commands for COUPONS of the COMPANY
	String SELECT_COUPON = "SELECT * FROM coupon WHERE id = ? AND company_id = ?";
	String SELECT_COUPONS = "SELECT * FROM coupon WHERE company_id = ?";

}
