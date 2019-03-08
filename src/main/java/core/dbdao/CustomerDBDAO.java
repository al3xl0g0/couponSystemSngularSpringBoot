package core.dbdao;

import core.beans.Coupon;
import core.beans.Customer;
import core.exceptions.CouponSystemException;
import core.exceptions.LoginFailedException;
import core.logger.CouponSystemLogger;
import core.dao.CustomerDAO;
import core.exceptions.couponExceptions.CouponDaoException;
import core.exceptions.customerExceptions.CustomerDaoException;
import core.pool.ConnectionPool;
import core.sql_commands.SQL_CUSTOMER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

public class CustomerDBDAO implements CustomerDAO {

    private static final CouponSystemLogger LOGGER = CouponSystemLogger.getInstance(CustomerDBDAO.class.getName());
    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void createCustomer(Customer customer) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.CREATE)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Creating customer is failed. ", e);
            throw new CustomerDaoException("Creating new customer in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void removeCustomer(long customerID) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.DELETE)) {
            pstmt.setLong(1, customerID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Removing customer is failed. ", e);
            throw new CustomerDaoException("Removing customer ID: " + customerID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.UPDATE)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPassword());
            pstmt.setString(3, customer.getEmail());
            pstmt.setLong(4, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Updating customer is failed. ", e);
            throw new CustomerDaoException("Updating customer ID: " + customer.getId() + " in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Customer getCustomerById(long customerID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Customer customer = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT_BY_ID)) {
            pstmt.setLong(1, customerID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    customer = new Customer();
                    customer.setId(customerID);
                    customer.setName(resultSet.getString("CUSTOMER_NAME"));
                    customer.setPassword(resultSet.getString("PASSWORD"));
                    customer.setEmail(resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting customer by ID is failed. ", e);
            throw new CustomerDaoException("Getting customer ID: " + customerID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return customer;
    }

    @Override
    public Customer getCustomerByName(String customerName) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Customer customer = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT_BY_NAME)) {
            pstmt.setString(1, customerName);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    customer = new Customer();
                    customer.setId(resultSet.getLong("ID"));
                    customer.setName(customerName);
                    customer.setPassword(resultSet.getString("PASSWORD"));
                    customer.setEmail(resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting customer by name is failed. ", e);
            throw new CustomerDaoException("Getting customer by name: " + customerName + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return customer;
    }

    @Override
    public Collection<Customer> getAllCustomers() throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Customer> customers;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT);
             ResultSet resultSet = pstmt.executeQuery()) {

            customers = new ArrayList<>();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("ID"));
                customer.setName(resultSet.getString("CUSTOMER_NAME"));
                customer.setPassword(resultSet.getString("PASSWORD"));
                customer.setEmail(resultSet.getString("EMAIL"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting all customers is failed. ", e);
            throw new CustomerDaoException("Getting all customers from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return customers;
    }

    @Override
    public Coupon getCustomerCoupon(long couponID, long customerID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Coupon coupon = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT_COUPON)) {
            pstmt.setLong(1, couponID);
            pstmt.setLong(2, customerID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    coupon = new CouponDBDAO().getCouponById(resultSet.getLong("COUPON_ID"));
                }
            }
        } catch (SQLException | CouponDaoException e) {
            LOGGER.log(Level.WARNING, "Getting customer coupon is failed. ", e);
            throw new CustomerDaoException(
                    "Getting coupon ID: " + couponID + " of customer ID: " + customerID + " from database is failed. ",
                    e);
        } finally {
            pool.returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public Collection<Coupon> getAllCustomerCoupons(long customerID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Coupon> customerCoupons;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT_COUPONS)) {
            pstmt.setLong(1, customerID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                customerCoupons = new ArrayList<>();

                while (resultSet.next()) {
                    Coupon coupon = new CouponDBDAO().getCouponById(resultSet.getLong("COUPON_ID"));
                    customerCoupons.add(coupon);
                }
            }
        } catch (SQLException | CouponDaoException e) {
            LOGGER.log(Level.WARNING, "Getting all customer coupons is failed. ", e);
            throw new CustomerDaoException(
                    "Getting all coupons of the customer ID: " + customerID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return customerCoupons;
    }

    @Override
    public void linkCustomerCoupon(long customerID, long couponID) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.LINK_COUPON)) {
            pstmt.setLong(1, customerID);
            pstmt.setLong(2, couponID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Linking customer to the coupon is failed. ", e);
            throw new CustomerDaoException("Linking customer to the coupon is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean login(String customerName, String customerPassword) throws CouponSystemException {

        Connection connection = pool.getConnection();
        String customerPasswordDB;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_CUSTOMER.SELECT_BY_NAME)) {
            pstmt.setString(1, customerName);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    customerPasswordDB = resultSet.getString("PASSWORD");

                    if (customerPassword.equals(customerPasswordDB)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Authorization is failed. ", e);
            throw new LoginFailedException("Authorization is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return false;
    }
}
