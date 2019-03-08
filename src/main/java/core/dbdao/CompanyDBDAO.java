package core.dbdao;

import core.beans.Company;
import core.beans.Coupon;
import core.exceptions.CouponSystemException;
import core.exceptions.LoginFailedException;
import core.logger.CouponSystemLogger;
import core.dao.CompanyDAO;
import core.exceptions.companyExceptions.CompanyDaoException;
import core.exceptions.couponExceptions.CouponDaoException;
import core.pool.ConnectionPool;
import core.sql_commands.SQL_COMPANY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

public class CompanyDBDAO implements CompanyDAO {

    private static final CouponSystemLogger LOGGER = CouponSystemLogger.getInstance(CompanyDBDAO.class.getName());
    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void createCompany(Company company) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.CREATE)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getPassword());
            pstmt.setString(3, company.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Creating company is failed. ", e);
            throw new CompanyDaoException("Creating new company in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void removeCompany(long companyID) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.DELETE)) {
            pstmt.setLong(1, companyID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Removing company is failed. ", e);
            throw new CompanyDaoException("Removing company ID: " + companyID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void updateCompany(Company company) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.UPDATE)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getPassword());
            pstmt.setString(3, company.getEmail());
            pstmt.setLong(4, company.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Updating company is failed. ", e);
            throw new CompanyDaoException("Updating company ID: " + company.getId() + " in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Company getCompanyById(long companyID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Company company = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT_BY_ID)) {
            pstmt.setLong(1, companyID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    company = new Company();
                    company.setId(companyID);
                    company.setName(resultSet.getString("COMPANY_NAME"));
                    company.setPassword(resultSet.getString("PASSWORD"));
                    company.setEmail(resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting company by ID is failed. ", e);
            throw new CompanyDaoException("Getting company ID: " + companyID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return company;
    }

    @Override
    public Company getCompanyByName(String companyName) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Company company = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT_BY_NAME)) {
            pstmt.setString(1, companyName);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    company = new Company();
                    company.setId(resultSet.getLong("ID"));
                    company.setName(companyName);
                    company.setPassword(resultSet.getString("PASSWORD"));
                    company.setEmail(resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting company by name is failed. ", e);
            throw new CompanyDaoException("Getting company by name: " + companyName + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return company;
    }

    @Override
    public Collection<Company> getAllCompanies() throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Company> companies;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT);
             ResultSet resultSet = pstmt.executeQuery()) {

            companies = new ArrayList<>();

            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("ID"));
                company.setName(resultSet.getString("COMPANY_NAME"));
                company.setPassword(resultSet.getString("PASSWORD"));
                company.setEmail(resultSet.getString("EMAIL"));
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting all companies is failed. ", e);
            throw new CompanyDaoException("Getting all companies from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return companies;
    }

    @Override
    public Coupon getCompanyCoupon(long couponID, long companyID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Coupon coupon = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT_COUPON)) {
            pstmt.setLong(1, couponID);
            pstmt.setLong(2, companyID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    coupon = new CouponDBDAO().getCouponById(resultSet.getLong("ID"));
                }
            }
        } catch (SQLException | CouponDaoException e) {
            LOGGER.log(Level.WARNING, "Getting company coupon is failed. ", e);
            throw new CompanyDaoException(
                    "Getting coupon ID: " + couponID + " of company ID: " + companyID + " from database is failed. ",
                    e);
        } finally {
            pool.returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public Collection<Coupon> getAllCompanyCoupons(long companyID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Coupon> companyCoupons;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT_COUPONS)) {
            pstmt.setLong(1, companyID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                companyCoupons = new ArrayList<>();

                while (resultSet.next()) {
                    Coupon coupon = new CouponDBDAO().getCouponById(resultSet.getLong("ID"));
                    companyCoupons.add(coupon);
                }
            }
        } catch (SQLException | CouponDaoException e) {
            LOGGER.log(Level.WARNING, "Getting all company coupons is failed. ", e);
            throw new CompanyDaoException(
                    "Getting all coupons of the company ID: " + companyID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return companyCoupons;
    }

    @Override
    public boolean login(String companyName, String companyPassword) throws CouponSystemException {

        Connection connection = pool.getConnection();
        String companyPasswordDB;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COMPANY.SELECT_BY_NAME)) {
            pstmt.setString(1, companyName);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    companyPasswordDB = resultSet.getString("PASSWORD");

                    if (companyPassword.equals(companyPasswordDB)) {
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
